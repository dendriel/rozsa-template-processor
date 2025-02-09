package com.vrozsa.tokens;

public class SwitchToken extends Token {
    public SwitchToken(TokenInput input) {
        super(type(), input);
    }

    public static TokenType type() {
        return TokenType.SWITCH;
    }

    @Override
    public void read() {

    }
}
