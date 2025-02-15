package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.operators.AbstractOperatorToken;

import static java.util.Objects.isNull;

/**
 * Holds a token with condition semantics.
 */
public class Condition extends AbstractToken {
    private final Token conditionToken;

    private final AbstractOperatorToken operator;

    public Condition(Token token) {
        this(token, null);
    }

    @Override
    public void read() {
        conditionToken.read();
    }

    public Condition(Token token, AbstractOperatorToken operator) {
        super(TokenType.CONDITION, token.input());

        conditionToken = token;
        this.operator = operator;
    }

    @Override
    public int endIdx() {
        if (isNull(operator)) {
            return conditionToken.endIdx();
        }

        return operator.endIdx();
    }

    @Override
    public TokenInput input() {
        return new TokenInput("", startIdx(), endIdx(), content());
    }

    @Override
    public Boolean evaluate(ContextHolder context) {
        var leftSideResult = conditionToken.evaluate(context);

        if (isNull(operator)) {
            // maybe cast from string
            return (Boolean) leftSideResult;
        }

        return operator.evaluate(context, leftSideResult);
    }
}
