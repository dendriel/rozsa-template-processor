package com.vrozsa.exceptions;

public class IncompleteTokenException extends TemplateProcessorException {
    public IncompleteTokenException(int index) {
        super(String.format("The content has finished with an incomplete token at index %d", index));
    }
}
