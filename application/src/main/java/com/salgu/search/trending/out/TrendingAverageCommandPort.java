package com.salgu.search.trending.out;

import com.salgu.search.TrendingAverage;

public interface TrendingAverageCommandPort {

    TrendingAverage update(TrendingAverage trendingAverage);
}
