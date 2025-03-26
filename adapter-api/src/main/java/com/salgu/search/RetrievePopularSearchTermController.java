package com.salgu.search;

import com.salgu.search.popular.in.RetrievePopularSearchTermQuery;
import com.salgu.search.popular.in.RetrievePopularSearchTermUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetrievePopularSearchTermController {

    private final RetrievePopularSearchTermUseCase retrievePopularSearchTermUseCase;

    @GetMapping("/search-terms")
    public List<String> retrieve(@RequestParam int limit) {
        RetrievePopularSearchTermQuery query = new RetrievePopularSearchTermQuery(limit);

        SearchTermCollection searchTerms = retrievePopularSearchTermUseCase.retrieve(query);

        return searchTerms.getSearchTerms().stream()
                .map(SearchTerm::getKeyword)
                .toList();
    }
}
