package com.vrozsa.tokens.functions;

import com.vrozsa.ContextHolder;
import com.vrozsa.exceptions.NonTextContextVariableException;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Locale;

public class LowercaseToken extends AbstractFunctionToken {
    public LowercaseToken(TokenInput input) {
        super(TokenType.LOWERCASE, input);
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var result = super.evaluate(context);

        if (result instanceof String textResult) {
            return textResult.toLowerCase(Locale.ROOT);
        }

        throw new NonTextContextVariableException(variable.keyword());
    }
}
