package com.salgu.search;

public class Average {

    private long score;

    private Average(long score) {
        this.score = score;
    }

    public static Average of(long score) {
        return new Average(score);
    }

    public static Average defaultAverage() {
        return new Average(1L);
    }

    public long getScore() {
        return score;
    }

    public Average calculateNewAverage(long count) {
        long newScore = (score + count) / 2;
        return new Average(newScore);
    }
}
