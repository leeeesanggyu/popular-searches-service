package com.salgu.search;

import com.salgu.search.out.PopularSearchTermCommandPort;
import com.salgu.search.out.PopularSearchTermQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PopularSearchTermAdapter implements PopularSearchTermCommandPort, PopularSearchTermQueryPort {

    private static final String SEARCH_TERM_KEY = "popular_search_term";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public SearchTerm save(SearchTerm searchTerm) {
        redisTemplate.opsForZSet().incrementScore(SEARCH_TERM_KEY, searchTerm.getKeyword(), 1);
        return searchTerm;
    }

    @Override
    public SearchTermCollection find(int limit) {
        Set<String> keywords = redisTemplate.opsForZSet().reverseRange(SEARCH_TERM_KEY, 0, limit - 1);
        List<SearchTerm> searchTerms = keywords.stream()
                .map(SearchTerm::of)
                .toList();
        return SearchTermCollection.of(searchTerms);
    }
}
