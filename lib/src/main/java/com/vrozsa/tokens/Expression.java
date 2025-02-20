package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.UnexpectedCharacterException;
import com.vrozsa.scanners.MainTokenScanner;

import static com.vrozsa.Reader.assertValidIndex;
import static java.util.Objects.isNull;

public class Expression implements Token {
    private static final char START_BRACKET = '{';
    public static final char END_BRACKET = '}';

    private final int startIdx;
    private final char[] content;
    private boolean hasStartBracket;
    private int endIdx;
    private Object result;
    private Token token;

    public Expression(int startIdx, char[] content) {
        this.startIdx = startIdx;
        this.content = content;
    }

    @Override
    public int startIdx() {
        return startIdx;
    }

    @Override
    public int endIdx() {
        return endIdx;
    }

    @Override
    public TokenType type() {
        return TokenType.EXPRESSION;
    }

    @Override
    public TokenInput input() {
        return new TokenInput("", startIdx, endIdx, content);
    }

    public int length() {
        return endIdx - startIdx;
    }

    /**
     * Reads the content of the expression until its closing bracket
     */
    public void read() {
        var nextIdx = startIdx;

        // Skip the starting expression bracket if necessary.
        if (content[nextIdx] == START_BRACKET) {
            hasStartBracket = true;
            nextIdx++;
        }

        var next = MainTokenScanner.instance().findNext(nextIdx, content);
        if (next.isEmpty()) {
            throw new InvalidSyntaxException("Could not find a valid token in the expression", startIdx);
        }

        token = next.get();
        //System.out.println("Token found: " + token);

        token.read();

        nextIdx = token.endIdx();

        if (!hasStartBracket) {
            endIdx = nextIdx;
            return;
        }

        // Go to next char after the token.
        nextIdx++;
        nextIdx = Reader.nextValidCharIndex(nextIdx, content);

        assertValidIndex(nextIdx, content);
        if (content[nextIdx] != END_BRACKET) {
            throw new UnexpectedCharacterException(END_BRACKET, content[nextIdx], nextIdx);
        }

        endIdx = nextIdx;
    }

    @Override
    public Object evaluate(ContextHolder context) {
        result = token.evaluate(context);
        if (isNull(result)) {
            result = "";
        }

        return result;
    }

    @Override
    public Object result() {
        return result;
    }

    @Override
    public String keyword() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Expression{" +
                "token=" + token +
                '}';
    }
}
