package com.salgu.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchTermCount {

    private String keyword;
    private long count;

    public static SearchTermCount fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, SearchTermCount.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static SearchTermCount of(String keyword, long count) {
        return new SearchTermCount(keyword, count);
    }
}
