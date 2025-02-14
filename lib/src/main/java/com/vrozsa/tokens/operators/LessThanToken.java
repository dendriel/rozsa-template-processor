package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class LessThanToken extends NumericOperatorToken {
    public LessThanToken(TokenInput input) {
        super(TokenType.LESS_THAN, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        return compareNumbers(leftSide, rightSide) < 0;
    }
}
