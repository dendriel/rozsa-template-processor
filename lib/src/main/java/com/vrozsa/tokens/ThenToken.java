package com.vrozsa.tokens;

public class ThenToken extends ContextVariableCompanionToken {
    public ThenToken(TokenInput input) {
        super(TokenType.THEN, input);
    }

    @Override
    public String toString() {
        return "ThenToken{" +
                "variable=" + variable +
                '}';
    }
}
