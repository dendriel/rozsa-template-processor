package com.vrozsa.exceptions;

public class InvalidIndexFormatException extends TemplateProcessorException {
    public InvalidIndexFormatException(String idxText) {
        super(String.format("Failed to parse index \"%s\". Invalid format. Should be an integer", idxText));
    }
}
