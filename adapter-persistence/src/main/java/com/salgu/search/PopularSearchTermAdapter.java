package com.salgu.search;

import com.salgu.search.out.PopularSearchTermCommandPort;
import com.salgu.search.out.PopularSearchTermQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularSearchTermAdapter implements PopularSearchTermCommandPort, PopularSearchTermQueryPort {

    private final PopularSearchTermRepository popularSearchTermRepository;

    @Override
    public SearchTerm save(SearchTerm searchTerm) {
        PopularSearchTermEntity popularSearchTermEntity = PopularSearchTermEntity.save(searchTerm);
        return popularSearchTermRepository.save(popularSearchTermEntity).toDomain();
    }

    @Override
    public void save(SearchTerm searchTerm, long count) {
        // TODO: Bulk Save 구현해야됨
    }

    @Override
    public SearchTermCollection find(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = popularSearchTermRepository.findKeywordsOrderedByCount(pageable);

        List<SearchTerm> searchTerms = results.stream()
                .map(result -> result[0].toString())
                .map(SearchTerm::of)
                .toList();
        return SearchTermCollection.of(searchTerms);
    }
}
