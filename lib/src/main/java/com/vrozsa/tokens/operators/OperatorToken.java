package com.vrozsa.tokens.operators;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidOperationException;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.UnexpectedTokenException;
import com.vrozsa.scanners.ContextVariableScanner;
import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static com.vrozsa.tokens.TokenType.CONTEXT_VARIABLE;

public abstract class OperatorToken extends Token {
    protected Token rightSideToken;

    protected OperatorToken(TokenType type, TokenInput input) {
        super(type, input);
    }

    @Override
    public void read() {
        // Next element after the token
        var startIdx = tokenEndIdx() + 1;

        startIdx = Reader.nextValidCharIndex(startIdx, content());

        var nextToken = ContextVariableScanner.instance().findNext(startIdx, content());
        if (nextToken.isEmpty()) {
            throw new InvalidSyntaxException("Invalid syntax found ", startIdx);
        }

        var token = nextToken.get();
        if (token instanceof ContextVariableToken contextVariableToken) {
            rightSideToken = contextVariableToken;
        }
        else {
            throw new UnexpectedTokenException(CONTEXT_VARIABLE, token.type(), startIdx);
        }

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
