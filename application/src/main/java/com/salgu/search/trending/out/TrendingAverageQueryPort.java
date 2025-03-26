package com.salgu.search.trending.out;

import com.salgu.search.SearchTerm;
import com.salgu.search.TrendingAverage;

import java.util.Optional;

public interface TrendingAverageQueryPort {

    Optional<TrendingAverage> findBy(SearchTerm searchTerm);
}
