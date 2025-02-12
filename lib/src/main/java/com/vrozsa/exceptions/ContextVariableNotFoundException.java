package com.vrozsa.exceptions;

public class ContextVariableNotFoundException extends TemplateProcessorException {
    public ContextVariableNotFoundException(String variable) {
        super(String.format("Variable %s was not found in the context.", variable));
    }
}
