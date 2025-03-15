package com.salgu.search;

public class SearchTerm {

    private final String keyword;

    private SearchTerm(String keyword) {
        this.keyword = keyword;
    }

    public static SearchTerm of(String searchTerm) {
        return new SearchTerm(searchTerm);
    }

    public static SearchTerm save(String searchTerm) {
        return new SearchTerm(searchTerm);
    }

    public String getKeyword() {
        return keyword;
    }
}
