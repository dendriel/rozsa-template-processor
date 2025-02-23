package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static java.util.Objects.isNull;

public class EqualsToken extends AbstractOperatorToken {
    public EqualsToken(TokenInput input) {
        super(TokenType.EQUALS, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        if(isNull(leftSide)) {
            return false;
        }
        return leftSide.equals(rightSide);
    }
}
