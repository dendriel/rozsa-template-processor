package com.vrozsa.tokens.operators;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.math.BigDecimal;

import static com.vrozsa.TypeConverter.asBigDecimal;
import static com.vrozsa.tokens.operators.AbstractNumericOperatorToken.compareNumbers;
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

        if (leftSide instanceof BigDecimal || rightSide instanceof BigDecimal) {
            return compareNumbers(leftSide, rightSide) == 0;
        }

        return leftSide.equals(rightSide);
    }
}
