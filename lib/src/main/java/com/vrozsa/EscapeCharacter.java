package com.vrozsa;

import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class EscapeCharacter implements Token {

    private final int startIdx;

    public EscapeCharacter(int startIdx) {
        this.startIdx = startIdx;
    }

    @Override
    public void read() {

    }

    @Override
    public int startIdx() {
        return startIdx;
    }

    @Override
    public int endIdx() {
        return startIdx + 1;
    }

    @Override
    public TokenInput input() {
        return null;
    }

    @Override
    public Object evaluate(ContextHolder context) {
        return null;
    }

    @Override
    public Object result() {
        // Returns nothing because we wan't to remove the escape character `\`.
        return "";
    }

    @Override
    public String keyword() {
        return null;
    }

    @Override
    public TokenType type() {
        return null;
    }
}
