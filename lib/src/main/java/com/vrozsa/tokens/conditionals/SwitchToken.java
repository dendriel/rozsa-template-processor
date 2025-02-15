package com.vrozsa.tokens.conditionals;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class SwitchToken extends AbstractToken {
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
