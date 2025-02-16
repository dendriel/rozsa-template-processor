package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;

public class Label implements Token {

    private final TokenInput input;
    private int endIdx;

    public Label(TokenInput tokenInput) {
        input = tokenInput;
    }

    @Override
    public void read() {
        endIdx = input.startIdx() + input.keyword().length();
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
        return null;
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
        return TokenType.LABEL;
    }
}
