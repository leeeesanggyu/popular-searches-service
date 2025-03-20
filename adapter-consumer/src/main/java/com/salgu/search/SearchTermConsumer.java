package com.salgu.search;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchTermConsumer {

    @KafkaListener(topics = "search-term", groupId = "search-term-group")
    public void consumeSearchTerm(String searchTerm) {
        System.out.println("consume = " + searchTerm);
    }
}
