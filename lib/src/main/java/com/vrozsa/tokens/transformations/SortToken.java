package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.TypeConverter;
import com.vrozsa.exceptions.InvalidLabelException;
import com.vrozsa.exceptions.InvalidOperationException;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.MissingContextVariableException;
import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class SortToken extends AbstractTransformationToken {
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\.");
    private SortOrderToken sortOrderToken;

    public SortToken(TokenInput input) {
        super(TokenType.SORT, input, false);
    }

    @Override
    public void read() {
        super.read();

        var nextIdx = Reader.nextValidCharIndex(endIdx + 1, content());
        scanSortOrderToken(nextIdx);
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

        var sortAsc = sortOrderToken.evaluate(context);

        if (isNull(asToken)) {
            sortByElement(listToSort, sortAsc);
        }
        else {
            sortByElementProp(listToSort, context, sortAsc);
        }

        return listToSort;
    }

    private static String getSortingProperty(String sortOnPropExpression, String entryLabel) {
        String[] split = DELIMITER_PATTERN.split(sortOnPropExpression);
        if (split.length != 2) {
            throw new InvalidLabelException("Expecting 2 tokens");
        }

        if (!split[0].equals(entryLabel)) {
            throw new InvalidLabelException(String.format("Unexpected token %s in label. Should be %s", split[0], entryLabel));
        }

        return split[1];
    }

    private static void sortByElement(List<?> listToSort, boolean sortAsc) {
        listToSort.sort((o1, o2) -> {
                Object value1 = sortAsc ? o1 : o2;
                Object value2 = sortAsc ? o2 : o1;

                if (value1 instanceof Comparable && value2 instanceof Comparable) {
                    return ((Comparable) value1).compareTo(value2);
                }
                return 0;
            }
        );
    }

    private void sortByElementProp(List<?> listToSort, ContextHolder context, boolean sortAsc) {
        var entryLabel = asToken.evaluate(context);
        var sortOnPropExpression = onToken.evaluate(context);

        var sortingProperty = getSortingProperty(sortOnPropExpression, entryLabel);

        Field field;
        try {
            field = listToSort.get(0).getClass().getDeclaredField(sortingProperty);
            field.setAccessible(true);
        }
        catch (Exception e) {
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
            }
            catch (Exception e) {
                throw new InvalidOperationException(String.format("The set %s couldn't be sorted.", setVarToken.keyword()), e);
            }
        });
    }

}
