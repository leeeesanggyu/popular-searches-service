package com.salgu.search;

import com.salgu.search.in.RetrievePopularSearchTermQuery;
import com.salgu.search.in.RetrievePopularSearchTermUseCase;
import com.salgu.search.in.SavePopularSearchTermCommand;
import com.salgu.search.in.SavePopularSearchTermUseCase;
import com.salgu.search.out.PopularSearchTermCommandPort;
import com.salgu.search.out.PopularSearchTermQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PopularSearchTermService implements SavePopularSearchTermUseCase, RetrievePopularSearchTermUseCase {

    private final PopularSearchTermCommandPort popularSearchTermCommandPort;
    private final PopularSearchTermQueryPort popularSearchTermQueryPort;

    @Transactional
    @Override
    public SearchTerm save(SavePopularSearchTermCommand command) {
        SearchTerm searchTerm = SearchTerm.save(command.searchTerm());

        return popularSearchTermCommandPort.save(searchTerm);
    }

    @Override
    public SearchTermCollection retrieve(RetrievePopularSearchTermQuery query) {
        return popularSearchTermQueryPort.find(query.limit());
    }
}
