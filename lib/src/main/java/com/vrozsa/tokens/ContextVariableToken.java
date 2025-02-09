package com.vrozsa.tokens;

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

    }
}
