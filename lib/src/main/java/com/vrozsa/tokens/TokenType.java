package com.vrozsa.tokens;

import java.util.Arrays;
import java.util.List;

public enum TokenType {
    CONTEXT_VARIABLE,
    CONDITION,
    CONDITION_GROUP,
    IF("IF"),
    THEN("THEN"),
    ELSE("ELSE"),
    SWITCH("SWITCH")
    ;


    private final List<String> keywords;

    TokenType(String...keywords) {
        this.keywords = Arrays.asList(keywords);
    }

    public boolean match(String name) {
        return keywords.stream()
                .anyMatch(key -> key.equals(name));
    }
}
