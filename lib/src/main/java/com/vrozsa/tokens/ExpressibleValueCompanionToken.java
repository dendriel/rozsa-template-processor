package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.scanners.ExpressibleValueScanner;

/**
 * Companion tokens should be followed by an expressible value to provide the final value.
 */
public abstract class ExpressibleValueCompanionToken extends AbstractToken {
    protected Token variable;

    protected ExpressibleValueCompanionToken(TokenType type, TokenInput input) {
        super(type, input);
    }

    @Override
    public void read() {
        // Next element after the token plus +1 for the group start char '('
        var startIdx = tokenEndIdx() + 1;

        startIdx = Reader.nextValidCharIndex(startIdx, content());

        read(startIdx);
    }

    // Allows skipping the grouping char '(' when evaluating for function tokens.
    public void read(int startIdx) {
        var nextToken = ExpressibleValueScanner.findNext(startIdx, content());
        if (nextToken.isEmpty()) {
            throw new RuntimeException("Invalid syntax close to index " + startIdx);
        }

        variable = nextToken.get();
        variable.read();
        endIdx = variable.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        return variable.evaluate(context);
    }
}
