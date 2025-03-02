package com.vrozsa.tokens.functions;

import com.vrozsa.ContextHolder;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

public class SumToken extends AbstractFunctionToken {
    public SumToken(TokenInput input) {
        super(TokenType.SUM, input);
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var sum = new BigDecimal(0);

        for (var token : params) {
            var val = token.evaluate(context);
            if (nonNull(val)) {
                sum = sum.add(new BigDecimal(val.toString()));
            }
        }

        return sum;
    }
}
