package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class LessThanEqualsToken extends AbstractNumericOperatorToken {
    public LessThanEqualsToken(TokenInput input) {
        super(TokenType.LESS_THAN_EQUALS, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        return compareNumbers(leftSide, rightSide) <= 0;
    }
}
