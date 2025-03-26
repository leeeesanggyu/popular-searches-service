package com.salgu.search.trending.in;

import com.salgu.search.SearchTerm;
import com.salgu.search.TrendingAverage;

public interface RetrieveTrendingAverageUseCase {

    TrendingAverage getPreviousAverage(SearchTerm searchTerm);
}
