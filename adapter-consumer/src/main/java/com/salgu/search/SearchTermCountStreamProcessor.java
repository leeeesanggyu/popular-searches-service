package com.salgu.search;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
public class SearchTermCountStreamProcessor {

    private static final String INPUT_TOPIC = "search-term";
    private static final String OUTPUT_TOPIC = "search-term-counts";

    @Bean
    public KStream<String, String> processSearchTerms(StreamsBuilder builder) {
        // 1. Kafka에서 검색어 스트림을 가져옴
        KStream<String, String> searchTermsStream = builder.stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        // 2. 검색어 기준으로 그룹핑 후, 10초 윈도우로 카운트 집계
        KTable<Windowed<String>, Long> searchTermCounts = searchTermsStream
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String())) // 검색어(value) 기준으로 그룹핑
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10))) // 10초 동안 집계
                .count(Materialized.as("search-term-counts")); // 카운트 결과를 KTable로 저장

        // 3. KTable을 KStream으로 변환하여 결과 전송
        KStream<String, SearchTermCount> aggregatedStream = searchTermCounts
                .toStream()
                .map((windowedKey, count) -> {
                    String keyword = windowedKey.key(); // 검색어 추출
                    return KeyValue.pair(keyword, SearchTermCount.of(keyword, count)); // 객체 변환
                });

        // 4. Kafka 토픽으로 발행
        aggregatedStream.to(OUTPUT_TOPIC, Produced.with(Serdes.String(), new JsonSerde<>(SearchTermCount.class)));
        return searchTermsStream;
    }
}
