package com.salgu.search.in;

import com.salgu.search.SearchTerm;

public interface SavePopularSearchTermUseCase {

    SearchTerm save(SavePopularSearchTermCommand command);
}
