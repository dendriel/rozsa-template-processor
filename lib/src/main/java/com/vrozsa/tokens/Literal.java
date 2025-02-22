package com.vrozsa.tokens;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.InvalidLiteralException;

import java.math.BigDecimal;

public class Literal implements Token {
    private static final CharacterChecker numberCharChecker = CharacterChecker.of(
            new CharacterRange(0, 9)
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
            result = evaluateNumber(context);
        }
        else {
            // It evaluates to a string.
            result = keyword();
        }

        return result;
    }


    private Object evaluateNumber(ContextHolder context) {
        try {
            var number = new BigDecimal(keyword());

            if (number.scale() <= 0) {
                var longValue = number.longValueExact();
                if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                    // Convert to Integer if it fits
                    return (int) longValue;
                }

                return longValue;
            }

            if (number.precision() <= 16) {
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
