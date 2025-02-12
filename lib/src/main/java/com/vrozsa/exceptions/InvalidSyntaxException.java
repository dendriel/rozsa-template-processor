package com.vrozsa.exceptions;

public class InvalidSyntaxException extends TemplateProcessorException {
    public InvalidSyntaxException(String error, int idx) {
        super(String.format("%s at index %d", error, idx));
    }
}
