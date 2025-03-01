package com.vrozsa.tokens.operators;

import com.vrozsa.exceptions.InvalidComparisonException;
import com.vrozsa.exceptions.NonNumericContextVariableException;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.vrozsa.TypeConverter.asBigDecimal;

abstract class AbstractNumericOperatorToken extends AbstractOperatorToken {
    AbstractNumericOperatorToken(TokenType type, TokenInput input) {
        super(type, input);
    }

    protected static int compareNumbers(Object leftSide, Object rightSide) {
        try {
            return asBigDecimal(leftSide)
                    .compareTo(asBigDecimal(rightSide));
        }
        catch (Exception e) {
            throw new InvalidComparisonException(leftSide, rightSide, e);
        }
    }

    /*
    protected static int compareNumbers(Object leftSide, Object rightSide) {
        try {
            if (leftSide instanceof Byte numericLeftSide) {
                return numericLeftSide.compareTo((Byte)rightSide);
            }
            else if (leftSide instanceof Short numericLeftSide) {
                return numericLeftSide.compareTo((Short)rightSide);
            }
            else if (leftSide instanceof Integer numericLeftSide) {
                return numericLeftSide.compareTo((Integer)rightSide);
            }
            else if (leftSide instanceof Long numericLeftSide) {
                return numericLeftSide.compareTo((Long)rightSide);
            }
            else if (leftSide instanceof Float numericLeftSide) {
                return numericLeftSide.compareTo((Float)rightSide);
            }
            else if (leftSide instanceof Double numericLeftSide) {
                return numericLeftSide.compareTo((Double)rightSide);
            }
            else if (leftSide instanceof BigInteger numericLeftSide) {
                return numericLeftSide.compareTo((BigInteger)rightSide);
            }
            else if (leftSide instanceof BigDecimal numericLeftSide) {
                return numericLeftSide.compareTo((BigDecimal)rightSide);
            }
            else {
                throw new NonNumericContextVariableException(leftSide);
            }
        }
        catch (NonNumericContextVariableException e) {
            throw e;
        }
        catch (Exception e) {
            throw new InvalidComparisonException(leftSide, rightSide, e);
        }
    }
     */
}
