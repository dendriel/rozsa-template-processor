package com.vrozsa.tokens;

import com.vrozsa.ContextVariableScanner;
import com.vrozsa.Expression;
import com.vrozsa.ExpressionScanner;
import com.vrozsa.StartingTokenScanner;

import java.util.Optional;

public class IfToken extends Token {
    private Token condition;
    private Token then;
    private Token orElse;

    public IfToken(TokenInput input) {
        super(type(), input);
    }

    public static TokenType type() {
        return TokenType.IF;
    }

    @Override
    public void read() {

        // Next element after the token
        var startIdx = input.endIdx() + 1;

        // TODO: handle expressions
//        if (ExpressionScanner.isNextTokenAnExpression(startIdx, input.content())) {
//            Expression[] expression = ExpressionScanner.scan(startIdx, input.content());
//        }

        Optional<Token> nextToken = ContextVariableScanner.instance().findNext(startIdx, input.content());
        if (nextToken.isEmpty()) {
            throw new RuntimeException("Invalid syntax close to index " + startIdx);
        }

        condition = nextToken.get();

    }
}
