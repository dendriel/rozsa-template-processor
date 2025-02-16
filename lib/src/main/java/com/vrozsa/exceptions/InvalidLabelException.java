package com.vrozsa.exceptions;

public class InvalidLabelException extends TemplateProcessorException {
    public InvalidLabelException(String message) {
        super(String.format("Invalid label used. %s", message));
    }
}
