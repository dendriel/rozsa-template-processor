package com.vrozsa.tokens;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;

public class TokenFactory<T extends Enum<T>> {

//    private final EnumMap<T, Function<TokenInput, Token>> providers;
//
//    public TokenFactory(EnumMap<T, Function<TokenInput, Token>> providers) {
//        this.providers = providers;
//    }
//
//    public Token newToken(int startIdx, int endIdx, char[] content) {
//        return tokenCreator.apply(new TokenInput(startIdx, endIdx, content));
//    }
//
//    public boolean anyMatch(String name) {
//        // TODO review the performance of this
//        return providers.keySet().stream()
//                .anyMatch(k -> k.match(name));
//    }
//
//    public static Optional<Token> create(String name, int startIdx, int endIdx, char[] content) {
//        for (var tokenType : values()) {
//            if (tokenType.type.match(name)) {
//                var newToken = tokenType.newToken(startIdx, endIdx, content);
//                return Optional.of(newToken);
//            }
//        }
//
//        return Optional.empty();
//    }
}
