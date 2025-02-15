package com.vrozsa.exceptions;

public class InvalidContextVariableException extends TemplateProcessorException {
    public InvalidContextVariableException(String keyword, Object value, String context, Throwable cause) {
        super(String.format("The context variable \"%s\" with value \"%s\" is not valid. %s", keyword, value, context), cause);
    }
}
