package com.vrozsa;

import com.vrozsa.tokens.StartingToken;
import com.vrozsa.tokens.Token;

import java.util.Optional;

public class StartingTokenScanner extends AbstractTokenScanner {
    private static final StartingTokenScanner INSTANCE = new StartingTokenScanner();

    public static StartingTokenScanner instance() {
        return INSTANCE;
    }

    @Override
    protected boolean matchAnyToken(String name) {
        return StartingToken.anyMatch(name);
    }

    @Override
    protected Optional<Token> createToken(String name, int startIdx, int endIdx, char[] content) {
        return StartingToken.create(name, startIdx, endIdx, content);
    }
}
