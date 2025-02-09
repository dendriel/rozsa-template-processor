package com.vrozsa.tokens;


public abstract class Token {
    protected final TokenType type;
    protected final TokenInput input;

    protected Token(TokenType type, TokenInput input) {
        this.type = type;
        this.input = input;
    }

    @Override
    public String toString() {
        return "Token{" +
                " type=" + type +
                //" name=" + String.valueOf(input.content()).substring(input.startIdx(), input.endIdx()+1) +
                " name=" + String.valueOf(input.content(), input.startIdx(), input.endIdx() + 1 - input.startIdx()) +
                " startIdx=" + input.startIdx() +
                " endIdx=" + input.endIdx() +
                " }";
    }
}
