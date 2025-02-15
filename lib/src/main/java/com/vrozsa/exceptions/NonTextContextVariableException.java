package com.vrozsa.exceptions;

public class NonTextContextVariableException extends TemplateProcessorException {
    public NonTextContextVariableException(Object value) {
        super(String.format("The context variable \"%s\" should be of string type.", value));
    }
}
