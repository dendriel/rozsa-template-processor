package com.vrozsa.exceptions;

import com.vrozsa.tokens.TokenType;

public class UnexpectedTokenException extends BaseLanguageProcessorException {

    private final TokenType expected;
    private final TokenType received;
    private final int index;

    public UnexpectedTokenException(TokenType expected, TokenType received, int index) {
        super(String.format("Unexpected token %s at %d. Expecting a %s token", received, index, expected));
        this.expected = expected;
        this.received = received;
        this.index = index;
    }

    public TokenType getExpected() {
        return expected;
    }

    public TokenType getReceived() {
        return received;
    }

    public int getIndex() {
        return index;
    }
}
