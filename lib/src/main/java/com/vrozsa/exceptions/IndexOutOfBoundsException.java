package com.vrozsa.exceptions;

public class IndexOutOfBoundsException extends TemplateProcessorException {
    public IndexOutOfBoundsException(Integer index, String keyword) {
        super(String.format("The index \"%d\" is outside of the bounds of the set \"%s\".", index, keyword));
    }
}
