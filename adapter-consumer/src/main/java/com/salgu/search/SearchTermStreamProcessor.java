package com.salgu.search;

import com.salgu.search.trending.in.RetrieveTrendingAverageUseCase;
import com.salgu.search.trending.in.UpdateTrendingAverageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SearchTermStreamProcessor {

    private static final String INPUT_TOPIC = "search-term";
    private static final String COUNTS_OUTPUT_TOPIC = "search-term-counts";
    private static final String TRENDING_OUTPUT_TOPIC = "search-term-trending";

    private final RetrieveTrendingAverageUseCase retrieveTrendingAverageUseCase;
    private final UpdateTrendingAverageUseCase updateTrendingAverageUseCase;

    @Bean
    public KStream<String, String> process(StreamsBuilder builder) {
        KStream<String, String> searchTerms = builder.stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        processSearchTermCounts(searchTerms);
        processSearchTermTrending(searchTerms);
        return searchTerms;
    }

    private void processSearchTermCounts(KStream<String, String> searchTermStream) {
        KTable<Windowed<String>, Long> searchTermCounts = searchTermStream
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String()))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
                .count(Materialized.as("search-term-counts"));

        KStream<String, SearchTermCount> aggregatedStream = searchTermCounts
                .toStream()
                .map((windowedKey, count) -> {
                    String keyword = windowedKey.key();
                    return KeyValue.pair(keyword, SearchTermCount.of(keyword, count));
                });

        aggregatedStream.to(COUNTS_OUTPUT_TOPIC, Produced.with(Serdes.String(), new JsonSerde<>(SearchTermCount.class)));
    }

    private void processSearchTermTrending(KStream<String, String> searchTerms) {
        // 검색어 빈도 집계
        KTable<Windowed<String>, Long> searchCounts = searchTerms
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String()))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(10))) // 1분 윈도우
                .count(Materialized.as("search-term-store"));

        // 이동 평균 기반 급상승 검색어 탐지
        searchCounts.toStream()
                .mapValues((key, count) -> detectTrendingKeyword(key.key(), count))
                .filter((key, isTrending) -> isTrending)
                .map((key, value) -> KeyValue.pair(key.key(), key.key()))
                .to(TRENDING_OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));
    }

    /**
     * 이동 평균을 기반으로 급상승 검색어를 탐지하는 메서드
     */
    private boolean detectTrendingKeyword(String keyword, Long currentCount) {
        // 이전 평균 빈도 조회
        TrendingAverage previousAverage = retrieveTrendingAverageUseCase.getPreviousAverage(SearchTerm.of(keyword));

        // 이동 평균 업데이트
        TrendingAverage updatedNewAverage = previousAverage.updateNewAverage(currentCount);
        updateTrendingAverageUseCase.updateAverage(updatedNewAverage);

        // 급상승 여부 판단
        return previousAverage.isTrendingKeyword(currentCount);
    }
}
