package com.salgu.search.popular.in;

import com.salgu.search.SearchTermCollection;

public interface RetrievePopularSearchTermUseCase {

    SearchTermCollection retrieve(RetrievePopularSearchTermQuery query);
}
