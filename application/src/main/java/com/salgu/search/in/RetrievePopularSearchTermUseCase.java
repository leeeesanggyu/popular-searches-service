package com.salgu.search.in;

import com.salgu.search.SearchTermCollection;

public interface RetrievePopularSearchTermUseCase {

    SearchTermCollection retrieve(RetrievePopularSearchTermQuery query);
}
