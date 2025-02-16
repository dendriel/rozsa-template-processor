package com.vrozsa.tokens;

import com.vrozsa.Reader;

public abstract class AbstractToken implements Token {
    private final TokenType type;
    protected final TokenInput input;

    protected int endIdx;

    protected Object result;

    protected AbstractToken(TokenType type, TokenInput input) {
        this.type = type;
        this.input = input;
    }

    @Override
    public TokenInput input() {
        return input;
    }

    @Override
    public Object result() {
        return result;
    }

    @Override
    public int startIdx() {
        return input.startIdx();
    }

    protected int tokenEndIdx() {
        return startIdx() + input.keyword().length() - 1;
    }

    /**
     * Returns the starting index to read the token's content.
     * @return the starting index of the token's content.
     */
    protected int contentStartIdx() {
        var startIdx = tokenEndIdx() + 1;
        return Reader.nextValidCharIndex(startIdx, content());
    }

    @Override
    public int endIdx() {
        return endIdx;
    }

    @Override
    public TokenType type() {
        return type;
    }

    public char[] content() {
        return input.content();
    }

    @Override
    public String keyword() {
        return input.keyword();
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
