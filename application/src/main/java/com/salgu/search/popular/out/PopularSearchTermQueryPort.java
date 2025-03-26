package com.salgu.search.popular.out;

import com.salgu.search.SearchTermCollection;

public interface PopularSearchTermQueryPort {

    SearchTermCollection find(int limit);
}
