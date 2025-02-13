package com.vrozsa.scanners;

import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Map;
import java.util.Optional;

/**
 * Handles context variables only.
 */
public class ContextVariableScanner extends AbstractTokenScanner {
    private static final ContextVariableScanner INSTANCE = new ContextVariableScanner();

    public static ContextVariableScanner instance() {
        return INSTANCE;
    }

    public ContextVariableScanner() {
        super(Map.of());
    }

    @Override
    protected boolean matchAnyToken(String name) {
        return true;
    }

    @Override
    protected Optional<Token> createToken(String keyword, int startIdx, int endIdx, char[] content) {
        return Optional.of(new ContextVariableToken(new TokenInput(keyword, startIdx, endIdx, content)));
    }
}
