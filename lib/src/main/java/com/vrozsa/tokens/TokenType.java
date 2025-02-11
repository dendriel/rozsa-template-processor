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
    SWITCH("SWITCH"),

    // OPERATORS
    EQUALS("EQUALS", "=="),
    NOT_EQUALS("NOT_EQUALS", "!="),
    GREATER_THAN("GREATER_THAN", ">"),
    GREATER_THAN_EQUALS("GREATER_THAN_EQUALS", ">="),
    LESS_THAN("LESS_THAN", "<"),
    LESS_THAN_EQUALS("LESS_THAN_EQUALS", "<="),
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
