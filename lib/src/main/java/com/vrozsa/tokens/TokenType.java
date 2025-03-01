package com.vrozsa.tokens;

import java.util.Arrays;
import java.util.List;

public enum TokenType {
    // ENTITIES
    CONTEXT_VARIABLE,
    EXPRESSION,
    CONDITION,
    LABEL,
    LITERAL,
    CONDITION_GROUP,

    // CONDITIONALS
    IF("IF"),
    THEN("THEN"),
    ELSE("ELSE"),
    SWITCH("SWITCH"),

    // FUNCTIONS
    UPPERCASE("UPPERCASE"),
    LOWERCASE("LOWERCASE"),
    IS_PRESENT("IS_PRESENT"),
    IS_ABSENT("IS_ABSENT"),
    NOT_EMPTY("NOT_EMPTY"),
    SUM("SUM"),

    // OPERATORS
    EQUALS("EQUALS", "=="),
    NOT_EQUALS("NOT_EQUALS", "!="),
    GREATER_THAN("GREATER_THAN", ">"),
    GREATER_THAN_EQUALS("GREATER_THAN_EQUALS", ">="),
    LESS_THAN("LESS_THAN", "<"),
    LESS_THAN_EQUALS("LESS_THAN_EQUALS", "<="),

    // TRANSFORMATIONS
    SORT("SORT"),
    AS("AS"),
    ON("ON"),
    SORT_ORDER("ASCENDING", "ASC", "DESCENDING", "DESC"),
    SET("SET"),
    FILTER("FILTER"),
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
