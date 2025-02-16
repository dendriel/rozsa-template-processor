package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidContextVariableTypeException;
import com.vrozsa.exceptions.InvalidLabelException;
import com.vrozsa.exceptions.InvalidOperationException;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.MissingContextVariableException;
import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.scanners.ExpressibleValueScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class SortToken extends AbstractToken {
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\.");
    private Token setVarToken;
    private AsToken asToken;
    private OnToken onToken;
    private SortOrderToken sortOrderToken;

    public SortToken(TokenInput input) {
        super(TokenType.SORT, input);
    }

    @Override
    public void read() {
        var startIdx = contentStartIdx();

        var nextIdx = scanSetVariableToken(startIdx);

        // Optional.
        var idxAfterAs = scanAsToken(nextIdx);

        if (idxAfterAs > nextIdx) {
            // If AS is present, ON is required
            nextIdx = scanOnToken(idxAfterAs);
        }

        scanSortOrderToken(nextIdx);
    }

    private int scanSetVariableToken(int startIdx) {
        Optional<Token> optTargetToken = ExpressibleValueScanner.findNext(startIdx, content());
        if (optTargetToken.isEmpty()) {
            throw new InvalidSyntaxException("No token found", startIdx);
        }

        setVarToken = optTargetToken.get();
        setVarToken.read();

        return Reader.nextValidCharIndex(setVarToken.endIdx() + 1, content());
    }

    private int scanAsToken(int startIdx) {
        var optAsToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optAsToken.isEmpty() || !optAsToken.get().type().equals(TokenType.AS)) {
            return startIdx;
        }

        asToken = (AsToken) optAsToken.get();
        asToken.read();

        return Reader.nextValidCharIndex(asToken.endIdx() + 1, content());
    }

    private int scanOnToken(int startIdx) {
        var optOnToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optOnToken.isEmpty() || !optOnToken.get().type().equals(TokenType.ON)) {
            throw new InvalidSyntaxException("ON token expected", startIdx);
        }

        onToken = (OnToken) optOnToken.get();
        onToken.read();

        return Reader.nextValidCharIndex(onToken.endIdx() + 1, content());
    }

    private void scanSortOrderToken(int startIdx) {
        var optSortOrderToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optSortOrderToken.isEmpty() || !optSortOrderToken.get().type().equals(TokenType.SORT_ORDER)) {
            throw new InvalidSyntaxException("Ordering token (ASC, DESC) expected", startIdx);
        }

        sortOrderToken = (SortOrderToken)optSortOrderToken.get();
        sortOrderToken.read();

        endIdx = sortOrderToken.endIdx();
    }

    // TODO: improve this code.
    @Override
    public Object evaluate(ContextHolder context) {

        Object set = setVarToken.evaluate(context);
        if (isNull(set)) {
            throw new MissingContextVariableException(setVarToken.keyword());
        }

        var listToSort = getSetAsList(set, setVarToken.keyword());

        if (listToSort.isEmpty()) {
            return null;
        }
        var sortAsc = sortOrderToken.evaluate(context);

        if (isNull(asToken)) {
            // Plain sort.
            listToSort.sort((o1, o2) -> {

                        Object value1 = sortAsc ? o1 : o2;
                        Object value2 = sortAsc ? o2 : o1;

                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            return ((Comparable) value1).compareTo(value2);
                        }
                        return 0;
                    }
            );

            return listToSort;
        }

        var entryLabel = asToken.evaluate(context);
        var sortOnPropExpression = onToken.evaluate(context);

        var sortingProperty = getSortingProperty(sortOnPropExpression, entryLabel);

        Field field;
        try {
            field = listToSort.get(0).getClass().getDeclaredField(sortingProperty);
            field.setAccessible(true);
        } catch (Exception e) {
            throw new InvalidOperationException(String.format("The field %s can't be used in the comparison.", sortingProperty), e);
        }

        listToSort.sort((o1, o2) -> {
            try {
                // Reverse vars accordingly to sorting order.
                Object value1 = sortAsc ? field.get(o1) : field.get(o2);
                Object value2 = sortAsc ? field.get(o2) : field.get(o1);

                if (value1 instanceof Comparable && value2 instanceof Comparable) {
                    return ((Comparable) value1).compareTo(value2);
                }
                return 0;
            } catch (Exception e) {
                throw new InvalidOperationException(String.format("The set %s couldn't be sorted.", setVarToken.keyword()), e);
            }

        });

        return listToSort;
    }

    private List<?> getSetAsList(Object set, String keyword) {
        if (set instanceof List<?> setAsList) {
            var listToSort = new ArrayList<>();
            listToSort.addAll(setAsList);
            return listToSort;
        }
        else if  (set instanceof Object[] setAsArray) {
            return Arrays.asList(setAsArray);
        }
        else if (set.getClass().isArray()) {
            int length = Array.getLength(set);
            List<Object> list = new ArrayList<>(length);
            for (var i = 0; i < length; i++) {
                list.add(Array.get(set, i));
            }

            return list;


        }

        throw new InvalidContextVariableTypeException(keyword, "Lists or Arrays");
    }


    private String getSortingProperty(String sortOnPropExpression, String entryLabel) {
        String[] split = DELIMITER_PATTERN.split(sortOnPropExpression);
        if (split.length != 2) {
            throw new InvalidLabelException("Expecting 2 tokens");
        }

        if (!split[0].equals(entryLabel)) {
            throw new InvalidLabelException(String.format("Unexpected token %s in label. Should be %s", entryLabel));
        }

        return split[1];
    }

}
