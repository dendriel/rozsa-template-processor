package com.vrozsa.tokens.conditionals;

import com.vrozsa.tokens.ExpressibleValueCompanionToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class ElseToken extends ExpressibleValueCompanionToken {
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
