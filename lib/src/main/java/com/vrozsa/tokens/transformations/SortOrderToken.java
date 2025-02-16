package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class SortOrderToken extends AbstractToken {
    public SortOrderToken(TokenInput input) {
        super(TokenType.SORT_ORDER, input);
    }

    @Override
    public void read() {
        endIdx = tokenEndIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        // TODO
        return null;
    }
}
