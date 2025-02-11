package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.UnexpectedTokenException;
import com.vrozsa.scanners.ContextVariableScanner;

import static com.vrozsa.tokens.TokenType.CONTEXT_VARIABLE;

/**
 * Companion tokens should have just a context variable to provide the final value.
 */
abstract class ContextVariableCompanionToken extends Token {
    protected ContextVariableToken variable;

    ContextVariableCompanionToken(TokenType type, TokenInput input) {
        super(type, input);
    }

    @Override
    public void read() {
        // Next element after the token
        var startIdx = tokenEndIdx() + 1;

        startIdx = Reader.nextValidCharIndex(startIdx, content());

        var nextToken = ContextVariableScanner.instance().findNext(startIdx, content());
        if (nextToken.isEmpty()) {
            throw new RuntimeException("Invalid syntax close to index " + startIdx);
        }

        var token = nextToken.get();
        if (token instanceof ContextVariableToken contextVariableToken) {
            variable = contextVariableToken;
        }
        else {
            throw new UnexpectedTokenException(CONTEXT_VARIABLE, token.type(), startIdx);
        }

        variable.read();
        endIdx = variable.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        return variable.evaluate(context);
    }
}
