package com.salgu.search.in;

import com.salgu.search.SearchTerm;

public record SavePopularSearchTermCommand(
        SearchTerm searchTerm
) {
}
