package com.vrozsa.tokens.operators;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidOperationException;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.scanners.ExpressibleValueScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

public abstract class AbstractOperatorToken extends AbstractToken {
    protected Token rightSideToken;

    protected AbstractOperatorToken(TokenType type, TokenInput input) {
        super(type, input);
    }

    @Override
    public void read() {
        // Next element after the token
        var startIdx = tokenEndIdx() + 1;
        startIdx = Reader.nextValidCharIndex(startIdx, content());

        var nextToken = ExpressibleValueScanner.findNext(startIdx, content());
        if (nextToken.isEmpty()) {
            throw new InvalidSyntaxException("Invalid syntax found ", startIdx);
        }

        rightSideToken = nextToken.get();
        rightSideToken.read();
        endIdx = rightSideToken.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        throw new InvalidOperationException(String.format("Plain evaluate can't be called for operations. Operator: %s", type()));
    }

    public boolean evaluate(ContextHolder contextHolder, Object leftSideResult) {
        var rightSideResult = rightSideToken.evaluate(contextHolder);

        return apply(leftSideResult, rightSideResult);
    }

    abstract boolean apply(Object leftSide, Object rightSide);

}
