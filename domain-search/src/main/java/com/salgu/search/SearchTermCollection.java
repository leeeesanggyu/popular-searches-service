package com.salgu.search;

import java.util.List;

public class SearchTermCollection {

    private final List<SearchTerm> searchTerms;

    private SearchTermCollection(List<SearchTerm> searchTerms) {
        this.searchTerms = searchTerms;
    }

    public static SearchTermCollection of(List<SearchTerm> searchTerm) {
        return new SearchTermCollection(searchTerm);
    }

    public List<SearchTerm> getSearchTerms() {
        return searchTerms;
    }
}
