package com.vrozsa.exceptions;

public class NonNumericContextVariableException extends TemplateProcessorException {
    public NonNumericContextVariableException(Object value) {
        super(String.format("The context variable value \"%s\" can't not be used in a numeric comparison.", value));
    }
}
