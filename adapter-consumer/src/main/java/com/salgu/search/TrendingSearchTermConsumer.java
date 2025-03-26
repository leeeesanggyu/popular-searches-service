package com.salgu.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrendingSearchTermConsumer {

    @KafkaListener(topics = "search-term-trending", groupId = "trending-search-term-log-group")
    public void consumeSearchTerm(String message) {
        log.info("Trending Search Term log: {}", message);
    }
}
