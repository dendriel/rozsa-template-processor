package com.vrozsa.tokens.conditionals;

import com.vrozsa.tokens.ExpressibleValueCompanionToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class ThenToken extends ExpressibleValueCompanionToken {
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
