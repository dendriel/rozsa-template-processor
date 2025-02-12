package com.vrozsa.exceptions;

public class TemplateProcessorException extends RuntimeException {

    public TemplateProcessorException(String message) {
        super(message);
    }

    public TemplateProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
