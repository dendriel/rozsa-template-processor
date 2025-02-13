package com.vrozsa.exceptions;

public class MissingContextVariableException extends TemplateProcessorException {
    public MissingContextVariableException(String contextVariableKey) {
        super(String.format("Variable %s was not found in the context.", contextVariableKey));
    }
}
