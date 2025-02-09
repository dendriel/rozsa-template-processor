package com.vrozsa.exceptions;

public class LanguageProcessorException extends RuntimeException {

    public LanguageProcessorException(String message) {
        super(message);
    }

    public LanguageProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
