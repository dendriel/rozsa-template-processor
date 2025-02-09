package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.ContextVariableNotFoundException;

import java.util.Optional;

public class ContextVariableToken extends Token {
    private final String variable;

    /**
     * Creates a new context variable token containing the name of the variable.
     * @param variable the name of the variable.
     * @param input token metadata.
     */
    public ContextVariableToken(String variable, TokenInput input) {
        super(TokenType.CONTEXT_VARIABLE, input);
        this.variable = variable;
    }

    @Override
    public void read() {
        endIdx = startIdx() + variable.length() - 1;
    }

    @Override
    public Object evaluate(ContextHolder context) {
        Optional<Object> optValue = context.get(variable);

        if (optValue.isEmpty()) {
            throw new ContextVariableNotFoundException(variable);
        }

        result = optValue.get();
        return result;
    }
}
