package com.vrozsa.tokens;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.ContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrozsa.TypeConverter;
import com.vrozsa.exceptions.IndexOutOfBoundsException;
import com.vrozsa.exceptions.InvalidContextVariableException;
import com.vrozsa.exceptions.InvalidIndexFormatException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class ContextVariable extends AbstractToken {
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\.");
    private static final Pattern INDEX_STARTER_PATTERN = Pattern.compile("\\[");

    private static final CharacterChecker endingIndexCharChecker = CharacterChecker.of(
            new CharacterSingle(']')
    );

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

    private Optional<Object> resolveVariable(ContextHolder context, String key) {

        var lastIdx = key.length() - 1;
        var lastChar = key.charAt(lastIdx);

        if (!endingIndexCharChecker.match(lastChar)) {
            // Resolve plain variable.
            return context.get(keyword());
        }

        // Resolve array.
        var keyTokens = INDEX_STARTER_PATTERN.split(key);
        if (keyTokens.length != 2) {
            // Handle leaf nodes only.
            return Optional.empty();
        }

        var nameToken = keyTokens[0];
        Optional<Object> optValue = context.get(nameToken);
        if (optValue.isEmpty()) {
            return Optional.empty();
        }

        List<?> setAsList = TypeConverter.getSetAsList(optValue.get(), nameToken);

        var idxToken = keyTokens[1];
        var targetIdx = parseIndex(idxToken);

        if (targetIdx >= setAsList.size()) {
            throw new IndexOutOfBoundsException(targetIdx, nameToken);
        }

        return Optional.of(setAsList.get(targetIdx));
    }

    private Integer parseIndex(String idxToken) {

        var indexBuilder = new StringBuilder();
        var currIdx = 0;

        // Skip the last character which is ']'.
        var lastValidIdx = idxToken.length() - 1;

        while (currIdx < lastValidIdx) {

            var currChar = idxToken.charAt(currIdx);
            indexBuilder.append(currChar);
            currIdx++;
        }

        try  {
            return Integer.valueOf(indexBuilder.toString());
        }
        catch (Exception e) {
            throw new InvalidIndexFormatException(indexBuilder.toString());
        }
    }

    @Override
    public Object evaluate(ContextHolder context) {
        // Find plain variables by keyword.
        Optional<Object> optValue = resolveVariable(context, keyword());
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
