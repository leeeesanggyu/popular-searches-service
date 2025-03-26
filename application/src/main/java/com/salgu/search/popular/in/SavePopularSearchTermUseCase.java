package com.salgu.search.popular.in;

import com.salgu.search.SearchTerm;

public interface SavePopularSearchTermUseCase {

    SearchTerm save(SavePopularSearchTermCommand command);

    void save(SavePopularSearchTermCountCommand command);
}
