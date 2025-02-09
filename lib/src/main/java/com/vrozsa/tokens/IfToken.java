package com.vrozsa.tokens;

public class IfToken extends Token {

    public IfToken(TokenInput input) {
        super(type(), input);
    }

    public static TokenType type() {
        return TokenType.IF;
    }
}
