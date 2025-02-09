package com.vrozsa.tokens;

public class SwitchToken extends Token {
    public SwitchToken(TokenInput input) {
        super(myType(), input);
    }

    public static TokenType myType() {
        return TokenType.SWITCH;
    }

    @Override
    public void read() {

    }
}
