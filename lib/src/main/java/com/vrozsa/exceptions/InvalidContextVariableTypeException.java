package com.vrozsa.exceptions;

public class InvalidContextVariableTypeException extends TemplateProcessorException {
    public InvalidContextVariableTypeException(String keyword, String expected) {
        super(String.format("The context variable \"%s\" has an invalid type. \"%s\" type expected.", keyword, expected));
    }
}
