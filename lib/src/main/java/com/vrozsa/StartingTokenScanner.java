package com.vrozsa;

import com.vrozsa.tokens.StartingToken;
import com.vrozsa.tokens.Token;

import java.util.Optional;

public class StartingTokenScanner extends TokenScanner {
    private static final StartingTokenScanner INSTANCE = new StartingTokenScanner();

    public static StartingTokenScanner instance() {
        return INSTANCE;
    }

    @Override
    protected boolean anyMatch(String name) {
        return StartingToken.anyMatch(name);
    }

    @Override
    protected Optional<Token> create(String name, int startIdx, int endIdx, char[] content) {
        return StartingToken.create(name, startIdx, endIdx, content);
    }
}
