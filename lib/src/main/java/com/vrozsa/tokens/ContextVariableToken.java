package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;

import java.util.Optional;

public class ContextVariableToken extends AbstractToken {

    /**
     * Creates a new context variable token containing the name of the variable.
     * @param input token metadata.
     */
    public ContextVariableToken(TokenInput input) {
        super(TokenType.CONTEXT_VARIABLE, input);
    }

    @Override
    public void read() {
        endIdx = tokenEndIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        Optional<Object> optValue = context.get(keyword());

        if (optValue.isEmpty()) {
            return null;
        }

        result = optValue.get();
        return result;
    }
}
