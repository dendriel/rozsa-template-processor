package com.vrozsa.tokens.transformations;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class OnToken extends LabelCompanionToken {
    public OnToken(TokenInput input) {
        super(TokenType.ON, input);
    }
}
