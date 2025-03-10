package com.vrozsa.tokens.functions;

import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.NonTextContextVariableException;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Locale;

public class UppercaseToken extends AbstractFunctionToken {
    public UppercaseToken(TokenInput input) {
        super(TokenType.UPPERCASE, input);
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var result = super.evaluate(context);

        if (result instanceof String textResult) {
            return textResult.toUpperCase(Locale.ROOT);
        }

        throw new NonTextContextVariableException(variable.keyword());
    }
}
