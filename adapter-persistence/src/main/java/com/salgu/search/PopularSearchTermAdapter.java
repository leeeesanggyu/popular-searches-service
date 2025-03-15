package com.salgu.search;

import com.salgu.search.out.PopularSearchTermCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularSearchTermAdapter implements PopularSearchTermCommandPort {

    private final PopularSearchTermRepository popularSearchTermRepository;

    @Override
    public SearchTerm save(SearchTerm searchTerm) {
        PopularSearchTermEntity popularSearchTermEntity = PopularSearchTermEntity.save(searchTerm);
        return popularSearchTermRepository.save(popularSearchTermEntity).toDomain();
    }
}
