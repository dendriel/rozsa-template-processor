package com.vrozsa.tokens.transformations;

import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class AsToken extends LabelCompanionToken {

    public AsToken(TokenInput input) {
        super(TokenType.AS, input);
    }
}
