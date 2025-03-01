package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public class NotEqualsToken extends AbstractOperatorToken {
    public NotEqualsToken(TokenInput input) {
        super(TokenType.NOT_EQUALS, input);
    }

    @Override
    boolean apply(Object leftSide, Object rightSide) {
        if(isNull(leftSide)) {
            return false;
        }

        if (leftSide instanceof BigDecimal || rightSide instanceof BigDecimal) {
            return AbstractNumericOperatorToken.compareNumbers(leftSide, rightSide) != 0;
        }

        return !leftSide.equals(rightSide);
    }
}
