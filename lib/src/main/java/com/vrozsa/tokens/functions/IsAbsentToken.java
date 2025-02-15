package com.vrozsa.tokens.functions;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static java.util.Objects.isNull;

public class IsAbsentToken extends AbstractFunctionToken {
    public IsAbsentToken(TokenInput input) {
        super(TokenType.IS_ABSENT, input);
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var result = super.evaluate(context);

        return isNull(result);
    }
}
