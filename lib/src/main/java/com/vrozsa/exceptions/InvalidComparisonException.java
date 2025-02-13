package com.vrozsa.exceptions;

/**
 * Thrown when comparing incompatible variables.
 */
public class InvalidComparisonException extends TemplateProcessorException {
    public InvalidComparisonException(Object leftSide, Object rightSide, Throwable cause) {
        super(String.format("The values \"%s\" and \"%s\" can't be compared.", leftSide, rightSide), cause);
    }
}
