package com.vrozsa.exceptions;

public class BaseLanguageProcessorException extends RuntimeException {

    public BaseLanguageProcessorException(String message) {
        super(message);
    }

    public BaseLanguageProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
