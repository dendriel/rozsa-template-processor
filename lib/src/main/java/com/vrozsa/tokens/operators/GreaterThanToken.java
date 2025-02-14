package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class GreaterThanToken extends AbstractNumericOperatorToken {
    public GreaterThanToken(TokenInput input) {
        super(TokenType.GREATER_THAN, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        return compareNumbers(leftSide, rightSide) > 0;
    }
}
