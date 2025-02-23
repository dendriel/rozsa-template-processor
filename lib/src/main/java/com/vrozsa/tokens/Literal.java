package com.vrozsa.tokens;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.CharacterSingle;
import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.InvalidLiteralException;
import com.vrozsa.scanners.LiteralScanner;

import java.math.BigDecimal;

public class Literal implements Token {
    private static final BigDecimal MAX_DOUBLE_VAL = BigDecimal.valueOf(Double.MAX_VALUE);
    private static final CharacterChecker numberCharChecker = CharacterChecker.of(
            new CharacterRange('0', '9')
    );

    private static final CharacterChecker escapeCharChecker = CharacterChecker.of(
            new CharacterSingle('\\')
    );

    private static final CharacterChecker allowedCharsToEscapeChecker = CharacterChecker.of(
            new CharacterSingle('"'),
            new CharacterSingle('\\')
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
            return result;
        }

        if (LiteralScanner.isBooleanLiteral(keyword())) {
            result = Boolean.parseBoolean(keyword());
            return result;
        }

//        result = keyword().substring(1, keyword().length() - 1);
        result = evaluateAsText();
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

    private Object evaluateAsText() {
        // remove the quotes
        var rawText = keyword().substring(1, keyword().length() - 1);

        // remove escapes
        var currIdx = 0;
        var resultBuilder = new StringBuilder();

        // do until before the last char
        while (currIdx < rawText.length() - 1) {

            var currChar = rawText.charAt(currIdx);
            if (escapeCharChecker.match(currChar)) {
                var nextChar = rawText.charAt(currIdx + 1);
                if (allowedCharsToEscapeChecker.match(nextChar)) {
                    // Append the next char and skip 2 positions.
                    resultBuilder.append(nextChar);
                    currIdx += 2;
                    continue;
                }
            }

            resultBuilder.append(currChar);
            currIdx++;
        }

        if (currIdx < rawText.length()) {
            var lastChar = rawText.charAt(currIdx);
            resultBuilder.append(lastChar);
        }

        return resultBuilder.toString();
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
