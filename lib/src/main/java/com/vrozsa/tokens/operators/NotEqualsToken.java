package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class NotEqualsToken extends AbstractOperatorToken {
    public NotEqualsToken(TokenInput input) {
        super(TokenType.NOT_EQUALS, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        if(isNull(leftSide)) {
            return false;
        }
        return !leftSide.equals(rightSide);
    }
}
