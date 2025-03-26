package com.salgu.search;

public class TrendingAverage {

    private SearchTerm searchTerm;
    private Average average;

    private TrendingAverage(SearchTerm searchTerm, Average average) {
        this.searchTerm = searchTerm;
        this.average = average;
    }

    public static TrendingAverage of(SearchTerm searchTerm, Average average) {
        return new TrendingAverage(searchTerm, average);
    }

    public static TrendingAverage defaultOf(SearchTerm searchTerm) {
        return new TrendingAverage(searchTerm, Average.defaultAverage());
    }

    public SearchTerm getSearchTerm() {
        return searchTerm;
    }

    public Average getAverage() {
        return average;
    }

    public TrendingAverage updateNewAverage(long count) {
        Average newAverage = average.calculateNewAverage(count);
        return TrendingAverage.of(searchTerm, newAverage);
    }

    public boolean isTrendingKeyword(long currentCount) {
        return currentCount >= average.getScore() * 2;
    }
}
