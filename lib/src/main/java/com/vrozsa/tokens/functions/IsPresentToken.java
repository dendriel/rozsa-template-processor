package com.vrozsa.tokens.functions;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static java.util.Objects.isNull;

public class IsPresentToken extends AbstractFunctionToken {
    public IsPresentToken(TokenInput input) {
        super(TokenType.IS_PRESENT, input);
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var result = super.evaluate(context);

        return !isNull(result);
    }
}
