package com.vrozsa;

import com.vrozsa.tokens.Token;

import java.util.Optional;

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

        System.out.println("Token found: " + next.get());

        token = next.get();

        token.read();
    }
}
