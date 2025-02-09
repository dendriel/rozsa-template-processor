package com.vrozsa;

import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Optional;

public class ContextVariableScanner extends TokenScanner {
    private static final ContextVariableScanner INSTANCE = new ContextVariableScanner();

    public static ContextVariableScanner instance() {
        return INSTANCE;
    }

    @Override
    protected boolean anyMatch(String name) {
        return true;
    }

    @Override
    protected Optional<Token> create(String name, int startIdx, int endIdx, char[] content) {
        return Optional.of(new ContextVariableToken(name, new TokenInput(startIdx, endIdx, content)));
    }
}
