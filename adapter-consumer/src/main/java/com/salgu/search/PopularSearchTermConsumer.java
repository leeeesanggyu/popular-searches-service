package com.salgu.search;

import com.salgu.search.popular.in.SavePopularSearchTermCountCommand;
import com.salgu.search.popular.in.SavePopularSearchTermUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class PopularSearchTermConsumer {

    private final SavePopularSearchTermUseCase savePopularSearchTermUseCase;

    @KafkaListener(topics = "search-term-counts", groupId = "popular-search-term-group")
    public void consumeSearchTerm(String message) {
        log.info("Consume Message: {}", message);
        if (checkMessageEmpty(message)) return;

        SearchTermCount searchTermCount = SearchTermCount.fromJson(message);

        SavePopularSearchTermCountCommand command = new SavePopularSearchTermCountCommand(
                SearchTerm.save(searchTermCount.getKeyword()),
                searchTermCount.getCount()
        );
        savePopularSearchTermUseCase.save(command);
    }

    private boolean checkMessageEmpty(String message) {
        return ObjectUtils.isEmpty(message);
    }
}
