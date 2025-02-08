package com.vrozsa.tokens;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public enum StartingToken {
    IF(List.of("IF"), IfToken::new),
    SWITCH(List.of("SWITCH"), SwitchToken::new),
//    FILTER,
//    UPPERCASE,
//    LOWERCASE
    ;

    private final Function<TokenInput, Token> tokenCreator;

    private final List<String> keywords;

    StartingToken(List<String> keywords, Function<TokenInput, Token> tokenCreator) {
        this.keywords = keywords;
        this.tokenCreator = tokenCreator;
    }

    public Token newToken(int startIdx, int endIdx, String content) {
        return tokenCreator.apply(new TokenInput(startIdx, endIdx, content));
    }

    public boolean match(String name) {
        return keywords.stream()
                .anyMatch(key -> key.equals(name));
    }

    public static boolean anyMatch(String name) {
        // TODO review the performance of this
        return Arrays.stream(values())
                .anyMatch(k -> k.match(name));
    }

    public static Optional<Token> create(String name, int startIdx, int endIdx, String content) {
        for (var tokenType : values()) {
            if (tokenType.match(name)) {
                var newToken = tokenType.newToken(startIdx, endIdx, content);
                return Optional.of(newToken);
            }
        }

        return Optional.empty();
    }
}
