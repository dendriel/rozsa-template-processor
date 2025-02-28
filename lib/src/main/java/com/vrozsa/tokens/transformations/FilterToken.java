package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.TypeConverter;
import com.vrozsa.exceptions.MissingContextVariableException;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class FilterToken extends AbstractTransformationToken {

    public FilterToken(TokenInput input) {
        super(TokenType.FILTER, input, true);
    }

    @Override
    public void read() {
        super.read();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        Object set = setVarToken.evaluate(context);
        if (isNull(set)) {
            throw new MissingContextVariableException(setVarToken.keyword());
        }

        var listToSort = TypeConverter.getSetAsList(set, setVarToken.keyword());
        if (listToSort.isEmpty()) {
            return null;
        }

        var elemAlias = asToken.evaluate(context);

        var newContext = ContextHolder.from(context);

        var filteredSet = new ArrayList<>();
        for(var elem : listToSort) {

            newContext.addCustom(elemAlias, elem);
            var include = (Boolean)onToken.evaluate(newContext);
            if (include) {
                filteredSet.add(elem);
            }
        }

        return filteredSet;
    }
}
