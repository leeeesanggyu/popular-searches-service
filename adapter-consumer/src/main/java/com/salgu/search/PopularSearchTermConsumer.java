package com.salgu.search;

import com.salgu.search.in.SavePopularSearchTermCommand;
import com.salgu.search.in.SavePopularSearchTermUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularSearchTermConsumer {

    private final SavePopularSearchTermUseCase savePopularSearchTermUseCase;

    @KafkaListener(topics = "search-term", groupId = "popular-search-term-group")
    public void consumeSearchTerm(String searchTerm) {
        System.out.println("Consume Message: " + searchTerm);
        SavePopularSearchTermCommand command = new SavePopularSearchTermCommand(searchTerm);
        savePopularSearchTermUseCase.save(command);
    }
}
