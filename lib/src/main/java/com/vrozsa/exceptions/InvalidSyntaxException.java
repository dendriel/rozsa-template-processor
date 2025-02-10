package com.vrozsa.exceptions;

public class InvalidSyntaxException extends LanguageProcessorException {
    public InvalidSyntaxException(String error, int idx) {
        super(String.format("%s at index %d", error, idx));
    }
}
