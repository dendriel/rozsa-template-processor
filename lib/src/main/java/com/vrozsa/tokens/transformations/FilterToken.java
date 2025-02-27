package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public class FilterToken extends AbstractToken {

    protected FilterToken(TokenInput input) {
        super(TokenType.FILTER, input);
    }

    @Override
    public void read() {

    }

    @Override
    public Object evaluate(ContextHolder context) {
        return null;
    }
}
