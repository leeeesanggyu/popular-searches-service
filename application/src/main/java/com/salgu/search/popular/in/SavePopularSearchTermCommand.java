package com.salgu.search.popular.in;

import com.salgu.search.SearchTerm;

public record SavePopularSearchTermCommand(
        SearchTerm searchTerm
) {
}
