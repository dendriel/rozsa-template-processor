package com.vrozsa.tokens;


import static com.vrozsa.tokens.TokenType.THEN;

public abstract class Token {
    private final TokenType type;
    private final TokenInput input;

    protected int endIdx;

    protected Token(TokenType type, TokenInput input) {
        this.type = type;
        this.input = input;
    }

    public abstract void read();

    public int startIdx() {
        return input.startIdx();
    }

    protected int tokenEndIdx() {
        return startIdx() + type.name().length() - 1;
    }

    public int endIdx() {
        return endIdx;
    }

    public TokenType type() {
        return type;
    }

    public char[] content() {
        return input.content();
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
