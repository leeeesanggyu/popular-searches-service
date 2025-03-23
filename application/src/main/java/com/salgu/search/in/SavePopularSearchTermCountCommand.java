package com.salgu.search.in;

import com.salgu.search.SearchTerm;

public record SavePopularSearchTermCountCommand(
        SearchTerm searchTerm,
        long count
) {
}
