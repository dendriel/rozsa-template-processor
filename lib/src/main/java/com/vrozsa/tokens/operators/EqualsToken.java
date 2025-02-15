package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class EqualsToken extends AbstractOperatorToken {
    public EqualsToken(TokenInput input) {
        super(TokenType.EQUALS, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        return leftSide.equals(rightSide);
    }
}
