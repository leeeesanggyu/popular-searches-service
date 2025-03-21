package com.salgu.search;

import com.salgu.message.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InputSearchTermController {

    private final MessageProducer messageProducer;

    @PostMapping("/search-terms")
    public String input(@RequestBody InputSearchTermPayload term) {
        messageProducer.send(term.keyword);
        return term.keyword;
    }

    public record InputSearchTermPayload(
            String keyword
    ) {

    }
}
