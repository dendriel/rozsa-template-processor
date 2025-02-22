package com.vrozsa.exceptions;

public class InvalidLiteralException extends TemplateProcessorException {
    public InvalidLiteralException(String literalText, Throwable cause) {
        super(String.format("The value \"%s\" could not be casted to any literal type.", literalText), cause);
    }
}
