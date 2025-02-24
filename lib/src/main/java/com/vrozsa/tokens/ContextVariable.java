package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrozsa.exceptions.InvalidContextVariableException;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class ContextVariable extends AbstractToken {
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\.");
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a new context variable token containing the name of the variable.
     * @param input token metadata.
     */
    public ContextVariable(TokenInput input) {
        super(TokenType.CONTEXT_VARIABLE, input);
    }

    @Override
    public void read() {
        endIdx = tokenEndIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        // Find plain variables by keyword.
        Optional<Object> optValue = context.get(keyword());
        if (optValue.isPresent()) {
            result = optValue.get();
            return result;
        }

        // Find navigational variables.

        String[] keywords = DELIMITER_PATTERN.split(keyword());
        if (keywords.length <= 1) {
            return result;
        }

        var idx = 0;

        // Find the first segment
        optValue = context.get(keywords[idx]);
        if (optValue.isEmpty()) {
            return result;
        }

        var fullKeyword = new StringBuilder(keywords[0]);
        var variableValue = optValue.get();

        // Find the remaining segments.
        while (++idx < keywords.length) {

            if (isNull(variableValue)) {
                break;
            }

            var variableContext = valueAsMap(variableValue, fullKeyword.toString());

            var nextKeyword = keywords[idx];
            if (!variableContext.containsKey(nextKeyword)) {
                variableValue = null;
                break;
            }

            variableValue = variableContext.get(nextKeyword);
            fullKeyword.append(".").append(nextKeyword);
        }

        result = variableValue;
        return result;
    }

    private Map<String, Object> valueAsMap(Object value, String keyword) {
        try {
            if (value instanceof Map result) {
                return (Map<String, Object>) result;
            }

            return mapper.convertValue(value, Map.class);
        }
        catch (Exception e) {
            throw new InvalidContextVariableException(keyword, value, "An object was expected.", e);
        }
    }
}
