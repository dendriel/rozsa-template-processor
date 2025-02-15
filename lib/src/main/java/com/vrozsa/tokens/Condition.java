package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.operators.AbstractOperatorToken;

import static java.util.Objects.isNull;

/**
 * Holds a token with condition semantics.
 */
public class Condition implements Token {
    private final Token conditionToken;
    private final AbstractOperatorToken operator;

    private Object result;

    public Condition(Token token) {
        this(token, null);
    }

    public Condition(Token token, AbstractOperatorToken operator) {
        conditionToken = token;
        this.operator = operator;
    }

    @Override
    public TokenType type() {
        return TokenType.CONDITION;
    }

    @Override
    public String keyword() {
        return conditionToken.keyword();
    }

    @Override
    public void read() {
        conditionToken.read();
    }

    @Override
    public int startIdx() {
        return conditionToken.startIdx();
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
        return new TokenInput("", conditionToken.startIdx(), endIdx(), null);
    }

    @Override
    public Boolean evaluate(ContextHolder context) {
        var leftSideResult = conditionToken.evaluate(context);

        if (isNull(operator)) {
            // maybe cast from string
            result = leftSideResult;
            return (Boolean) result;
        }

        result = operator.evaluate(context, leftSideResult);
        return (Boolean) result;
    }

    @Override
    public Object result() {
        return null;
    }
}
