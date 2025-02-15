package com.vrozsa.tokens.conditionals;

import com.vrozsa.tokens.ContextVariableCompanionToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

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
