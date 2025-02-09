package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;

/**
 * Holds a token with condition semantics.
 */
public class Condition extends Token {
    private final Token conditionToken;

    public Condition(Token token) {
        super(TokenType.CONDITION, token.input);

        conditionToken = token;
    }

    @Override
    public void read() {}

    @Override
    public Boolean evaluate(ContextHolder context) {
        return (Boolean) conditionToken.evaluate(context);
    }
}
