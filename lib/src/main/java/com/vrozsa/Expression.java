package com.vrozsa;

import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.UnexpectedCharacterException;
import com.vrozsa.scanners.MainTokenScanner;
import com.vrozsa.tokens.Token;

import static com.vrozsa.Reader.assertValidIndex;

public class Expression {
    private static final char START_BRACKET = '{';
    public static final char END_BRACKET = '}';

    private final int startIdx;
    private final char[] content;
    private boolean hasStartBracket;
    private int endIdx;
    private Token token;

    public Expression(int startIdx, char[] content) {
        this.startIdx = startIdx;
        this.content = content;
    }

    public int startIdx() {
        return startIdx;
    }

    public int endIdx() {
        return endIdx;
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
        System.out.println("Token found: " + token);

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

    public void evaluate(ContextHolder context) {
        token.evaluate(context);
    }

    public String getResult() {
        return String.valueOf(token.getResult());
    }

    @Override
    public String toString() {
        return "Expression{" +
                "token=" + token +
                '}';
    }
}
