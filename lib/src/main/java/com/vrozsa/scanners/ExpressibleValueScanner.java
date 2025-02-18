package com.vrozsa.scanners;

import com.vrozsa.tokens.Expression;
import com.vrozsa.Reader;
import com.vrozsa.tokens.Token;

import java.util.Optional;

/**
 * Used to scan fields which allow different inputs: Expressions, Functions and context-variables
 */
public class ExpressibleValueScanner {
    public static Optional<Token> findNext(final int idx, final char[] content) {
        var startIdx = Reader.nextValidCharIndex(idx, content);

        Token targetToken = null;

        if (ExpressionScanner.isNextTokenAnExpression(startIdx, content)) {
            var expressionStartIdx = startIdx + 1;
            targetToken = new Expression(expressionStartIdx, content);
        }
        else {
            var token = FunctionTokenScanner.instance().findNext(idx, content);
            if (token.isPresent()) {
                targetToken = token.get();
            }
        }

        return Optional.ofNullable(targetToken);
    }
}
