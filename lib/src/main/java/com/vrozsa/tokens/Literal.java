package com.vrozsa.tokens;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.InvalidLiteralException;

import java.math.BigDecimal;

public class Literal implements Token {
    private static final BigDecimal MAX_DOUBLE_VAL = BigDecimal.valueOf(Double.MAX_VALUE);
    private static final CharacterChecker numberCharChecker = CharacterChecker.of(
            new CharacterRange('0', '9')
    );

    private final TokenInput input;
    private int endIdx;
    private Object result;

    public Literal(TokenInput tokenInput) {
        input = tokenInput;
    }

    @Override
    public void read() {
        endIdx = input.startIdx() + input.keyword().length() - 1;
    }

    @Override
    public int startIdx() {
        return input.startIdx();
    }

    @Override
    public int endIdx() {
        return endIdx;
    }

    @Override
    public TokenInput input() {
        return input;
    }

    @Override
    public Object evaluate(ContextHolder context) {

        var firstChar = keyword().charAt(0);

        if (numberCharChecker.match(firstChar)) {
            result = evaluateAsNumber();
        }
        else {
            // It evaluates to a string.
            result = keyword().substring(1, keyword().length() - 1);
        }

        return result;
    }


    private Object evaluateAsNumber() {
        try {
            var number = new BigDecimal(keyword());

            // Check if the value is too large for double
            if (number.compareTo(MAX_DOUBLE_VAL) > 0) {
                return number;
            }

            if (number.scale() == 0) {
                var longValue = number.longValueExact();
                if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                    // Convert to Integer if it fits
                    return (int) longValue;
                }

                return longValue;
            }

            if (number.precision() <= 17) {
                return number.doubleValue();
            }

            return number;
        }
        catch (Exception e) {
            throw new InvalidLiteralException(keyword(), e);
        }
    }

    @Override
    public Object result() {
        return input.keyword();
    }

    @Override
    public String keyword() {
        return input.keyword();
    }

    @Override
    public TokenType type() {
        return TokenType.LITERAL;
    }
}
