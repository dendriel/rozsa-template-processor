package com.vrozsa.exceptions;

public class UnexpectedCharacterException extends TemplateProcessorException {
    private final char expected;
    private final char received;
    private final int index;

    public UnexpectedCharacterException(char expected, char received, int index) {
        super(String.format("Unexpected char '%s' at %d. Expecting a '%s' char", received, index, expected));

        this.expected = expected;
        this.received = received;
        this.index = index;
    }

    public char getExpected() {
        return expected;
    }

    public char getReceived() {
        return received;
    }

    public int getIndex() {
        return index;
    }
}
