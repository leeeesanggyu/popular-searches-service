package com.salgu.search;

import com.salgu.search.in.SavePopularSearchTermCommand;
import com.salgu.search.in.SavePopularSearchTermUseCase;
import com.salgu.search.out.PopularSearchTermCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PopularSearchTermsService implements SavePopularSearchTermUseCase {

    private final PopularSearchTermCommandPort popularSearchTermCommandPort;

    @Transactional
    @Override
    public SearchTerm save(SavePopularSearchTermCommand command) {
        SearchTerm searchTerm = SearchTerm.save(command.searchTerm());

        return popularSearchTermCommandPort.save(searchTerm);
    }
}
