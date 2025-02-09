package com.vrozsa;

import com.vrozsa.exceptions.UnexpectedCharacterException;
import com.vrozsa.tokens.Token;

import java.util.Optional;

import static com.vrozsa.Reader.assertValidIndex;

public class Expression {
    private static char START_BRACKET = '{';
    private static char END_BRACKET = '}';

    private int startIdx;
    private int endIdx;
    private char[] content;
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

    public void read() {
        var nextIdx = startIdx;

        // Skip the starting expression bracket if necessary.
        if (content[nextIdx] == START_BRACKET) {
            nextIdx++;
        }

        Optional<Token> next = StartingTokenScanner.instance().findNext(nextIdx, content);

        if (next.isEmpty()) {
            System.out.println("Token not found!");
            return;
        }

        token = next.get();
        System.out.println("Token found: " + token);

        token.read();

        nextIdx = token.endIdx() + 1;
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
        return (String) token.getResult();
    }

    @Override
    public String toString() {
        return "Expression{" +
                "token=" + token +
                '}';
    }
}
