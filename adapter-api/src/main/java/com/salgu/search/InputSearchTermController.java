package com.salgu.search;

import com.salgu.search.in.SavePopularSearchTermCommand;
import com.salgu.search.in.SavePopularSearchTermUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InputSearchTermController {

    private final SavePopularSearchTermUseCase savePopularSearchTermUseCase;

    @PostMapping("/search-terms")
    public String input(@RequestBody InputSearchTermPayload term) {
        log.info("input: {}", term);
        SavePopularSearchTermCommand command = new SavePopularSearchTermCommand(term.keyword);

        return savePopularSearchTermUseCase.save(command).getKeyword();
    }

    public record InputSearchTermPayload(
            String keyword
    ) {

    }
}
