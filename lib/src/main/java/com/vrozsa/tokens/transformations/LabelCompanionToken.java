package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.scanners.LabelScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Label;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

abstract class LabelCompanionToken extends AbstractToken {
    private Label label;

    LabelCompanionToken(TokenType type, TokenInput input) {
        super(type, input);
    }

    @Override
    public void read() {
        var startIdx = contentStartIdx();
        var nextToken = LabelScanner.instance().findNext(startIdx, content());
        if (nextToken.isEmpty()) {
            throw new InvalidSyntaxException("Expected a label token", startIdx);
        }

        label = nextToken.get();
        label.read();

        endIdx = label.endIdx();
    }

    @Override
    public String evaluate(ContextHolder context) {
        return label.keyword();
    }
}
