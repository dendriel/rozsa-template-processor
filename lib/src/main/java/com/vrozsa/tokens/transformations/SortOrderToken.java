package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class SortOrderToken extends AbstractToken {

    private boolean isAscending;

    public SortOrderToken(TokenInput input) {
        super(TokenType.SORT_ORDER, input);
    }

    @Override
    public void read() {
        endIdx = tokenEndIdx();
    }

    @Override
    public Boolean evaluate(ContextHolder context) {
        isAscending = keyword().startsWith("ASC");
        return isAscending;
    }

    public boolean isAsc() {
        return isAscending;
    }

    public boolean isDesc() {
        return !isAscending;
    }
}
