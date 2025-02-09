package com.vrozsa.tokens;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum StartingToken {
    IF(IfToken.type(), IfToken::new),
    SWITCH(SwitchToken.type(), SwitchToken::new),
//    FILTER,
//    UPPERCASE,
//    LOWERCASE
    ;

    private final TokenType type;

    private final Function<TokenInput, Token> tokenCreator;

    StartingToken(TokenType type, Function<TokenInput, Token> tokenCreator) {
        this.type = type;
        this.tokenCreator = tokenCreator;
    }

    public Token newToken(int startIdx, int endIdx, char[] content) {
        return tokenCreator.apply(new TokenInput(startIdx, endIdx, content));
    }

    public static boolean anyMatch(String name) {
        // TODO review the performance of this
        return Arrays.stream(values())
                .anyMatch(k -> k.type.match(name));
    }

    public static Optional<Token> create(String name, int startIdx, int endIdx, char[] content) {
        for (var tokenType : values()) {
            if (tokenType.type.match(name)) {
                var newToken = tokenType.newToken(startIdx, endIdx, content);
                return Optional.of(newToken);
            }
        }

        return Optional.empty();
    }
}
