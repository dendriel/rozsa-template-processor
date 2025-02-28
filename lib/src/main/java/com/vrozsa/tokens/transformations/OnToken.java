package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.ExpressibleValueCompanionToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class OnToken extends ExpressibleValueCompanionToken {
    public OnToken(TokenInput input) {
        super(TokenType.ON, input);
    }

    public String evaluateAsLabel(ContextHolder context) {
        return variable.keyword();
    }
}
