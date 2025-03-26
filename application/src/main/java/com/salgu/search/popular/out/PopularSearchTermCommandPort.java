package com.salgu.search.popular.out;

import com.salgu.search.SearchTerm;

public interface PopularSearchTermCommandPort {

    SearchTerm save(SearchTerm searchTerm);

    void save(SearchTerm searchTerm, long count);
}
