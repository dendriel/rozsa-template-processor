package com.vrozsa;

import com.vrozsa.tokens.Token;

import java.util.Optional;

class Expression {
    private static char START_BRACKET = '{';
    private static char END_BRACKET = '}';

    private int startIdx;
    private int endIdx;

    private String content;

    private Token token;

    public Expression(int startIdx, String content) {
        this.startIdx = startIdx;
        this.content = content;
    }

    public void evaluate() {
        var nextIdx = startIdx;

        // Skip the starting expression bracket if necessary.
        if (content.charAt(nextIdx) == START_BRACKET) {
            nextIdx++;
        }

        Optional<Token> next = TokenScanner.findNext(nextIdx, content);

        if (next.isPresent()) {
            System.out.println("Token found: " + next.get());
        }
        else {
            System.out.println("Token not found!");
        }
    }
}
