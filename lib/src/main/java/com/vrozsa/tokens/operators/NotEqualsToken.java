package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class NotEqualsToken extends OperatorToken {
    public NotEqualsToken(TokenInput input) {
        super(TokenType.NOT_EQUALS, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        return !leftSide.equals(rightSide);
    }
}
