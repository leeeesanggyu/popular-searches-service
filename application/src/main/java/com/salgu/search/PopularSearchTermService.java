package com.salgu.search;

import com.salgu.search.in.*;
import com.salgu.search.out.PopularSearchTermCommandPort;
import com.salgu.search.out.PopularSearchTermQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularSearchTermService implements SavePopularSearchTermUseCase, RetrievePopularSearchTermUseCase {

    private final PopularSearchTermCommandPort popularSearchTermCommandPort;
    private final PopularSearchTermQueryPort popularSearchTermQueryPort;

    @Override
    public SearchTerm save(SavePopularSearchTermCommand command) {
        return popularSearchTermCommandPort.save(command.searchTerm());
    }

    @Override
    public void save(SavePopularSearchTermCountCommand command) {
        popularSearchTermCommandPort.save(command.searchTerm(), command.count());
    }

    @Override
    public SearchTermCollection retrieve(RetrievePopularSearchTermQuery query) {
        return popularSearchTermQueryPort.find(query.limit());
    }
}
