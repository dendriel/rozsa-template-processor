package com.vrozsa.tokens;

public class ElseToken extends ContextVariableCompanionToken {
    public ElseToken(TokenInput input) {
        super(TokenType.ELSE, input);
    }

    @Override
    public String toString() {
        return "ElseToken{" +
                "variable=" + variable +
                '}';
    }
}
