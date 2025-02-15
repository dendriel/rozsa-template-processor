package com.vrozsa.exceptions;

public class NonNumericContextVariableException extends TemplateProcessorException {
    public NonNumericContextVariableException(Object value) {
        super(String.format("The context variable value \"%s\" should be of numeric type.", value));
    }
}
