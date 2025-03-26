package com.salgu.search;

import com.salgu.search.trending.out.TrendingAverageCommandPort;
import com.salgu.search.trending.out.TrendingAverageQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrendingAverageAdapter implements TrendingAverageQueryPort, TrendingAverageCommandPort {

    private static final String REDIS_KEY_PREFIX = "trending_average:"; // Redis Key Prefix

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public TrendingAverage update(TrendingAverage trendingAverage) {
        String key = REDIS_KEY_PREFIX + trendingAverage.getSearchTerm().getKeyword();
        redisTemplate.opsForValue().set(key, String.valueOf(trendingAverage.getAverage().getScore()));
        return trendingAverage;
    }

    @Override
    public Optional<TrendingAverage> findBy(SearchTerm searchTerm) {
        String key = REDIS_KEY_PREFIX + searchTerm.getKeyword();
        String averageValue = redisTemplate.opsForValue().get(key);

        if (ObjectUtils.isEmpty(averageValue)) {
            return Optional.empty();
        }

        return Optional.of(TrendingAverage.of(searchTerm, Average.of(Long.parseLong(averageValue))));
    }
}
