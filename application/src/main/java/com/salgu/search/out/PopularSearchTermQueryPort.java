package com.salgu.search.out;

import com.salgu.search.SearchTermCollection;

public interface PopularSearchTermQueryPort {

    SearchTermCollection find(int limit);
}
