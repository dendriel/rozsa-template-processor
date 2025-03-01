package com.vrozsa.tokens.functions;

import com.vrozsa.ContextHolder;
import com.vrozsa.TypeConverter;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.List;

public class NotEmptyToken extends AbstractFunctionToken {
    public NotEmptyToken(TokenInput input) {
        super(TokenType.NOT_EMPTY, input);
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var set = super.evaluate(context);

        List<?> setAsList = TypeConverter.getSetAsList(set, keyword());

        return !setAsList.isEmpty();
    }
}
