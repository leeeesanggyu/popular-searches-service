package com.salgu.search.trending;

import com.salgu.search.SearchTerm;
import com.salgu.search.TrendingAverage;
import com.salgu.search.trending.in.RetrieveTrendingAverageUseCase;
import com.salgu.search.trending.in.UpdateTrendingAverageUseCase;
import com.salgu.search.trending.out.TrendingAverageCommandPort;
import com.salgu.search.trending.out.TrendingAverageQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrendingAverageService implements UpdateTrendingAverageUseCase, RetrieveTrendingAverageUseCase {

    private final TrendingAverageQueryPort trendingAverageQueryPort;
    private final TrendingAverageCommandPort trendingAverageCommandPort;

    @Override
    public TrendingAverage getPreviousAverage(SearchTerm searchTerm) {
        Optional<TrendingAverage> foundTrendingAverage = trendingAverageQueryPort.findBy(searchTerm);
        return foundTrendingAverage.orElseGet(() -> TrendingAverage.defaultOf(searchTerm));
    }

    @Override
    public TrendingAverage updateAverage(TrendingAverage trendingAverage) {
        return trendingAverageCommandPort.update(trendingAverage);
    }
}
