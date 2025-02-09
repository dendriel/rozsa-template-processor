package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;

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

    @Override
    public Object evaluate(ContextHolder context) {
        return null;
    }
}
