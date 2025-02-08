package com.vrozsa.tokens;


public abstract class Token {
    protected final TokenInput input;

    protected Token(TokenInput input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "Token{" +
                " name=" + input.content().substring(input.startIdx(), input.endIdx()+1) +
                " startIdx=" + input.startIdx() +
                " endIdx=" + input.endIdx() +
                " }";
    }
}
