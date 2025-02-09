package com.vrozsa.tokens;

import com.vrozsa.AuxiliaryTokenScanner;
import com.vrozsa.ContextHolder;
import com.vrozsa.ContextVariableScanner;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.UnexpectedTokenException;

import java.util.Optional;

import static com.vrozsa.tokens.TokenType.CONTEXT_VARIABLE;
import static com.vrozsa.tokens.TokenType.THEN;
import static java.util.Objects.isNull;

public class IfToken extends Token {
    private Condition condition;
    private Token then;
    private Token orElse;

    private String result;

    public IfToken(TokenInput input) {
        super(myType(), input);
    }

    // TODO: will be changed
    public static TokenType myType() {
        return TokenType.IF;
    }

    @Override
    public void read() {

        var content = content();

        // Next element after the token
        var startIdx = tokenEndIdx() + 1;

        startIdx = Reader.nextValidCharIndex(startIdx, content);

        // First can be an expression, a conditional or a context variable

        // TODO: handle expressions
//        if (ExpressionScanner.isNextTokenAnExpression(startIdx, input.content())) {
//            Expression[] expression = ExpressionScanner.scan(startIdx, input.content());
//        }

        Optional<Token> nextToken = ContextVariableScanner.instance().findNext(startIdx, content);
        if (nextToken.isEmpty()) {
            throw new RuntimeException("Invalid syntax close to index " + startIdx);
        }

        var conditionToken = nextToken.get();
        conditionToken.read();
        condition = new Condition(conditionToken);

        var nextIdx = conditionToken.endIdx() + 1; // next char after the condition
        nextIdx = Reader.nextValidCharIndex(nextIdx, content);

        var thenToken = AuxiliaryTokenScanner.instance().findNext(nextIdx, content);
        if (thenToken.isEmpty()) {
            throw new RuntimeException("Couldn't find the next token from if");
        }

        // TODO: read conditionals

        // Next will be a THEN token.
        then = thenToken.get();
        if (!THEN.equals(then.type())) {
            throw new UnexpectedTokenException(THEN, then.type(), nextIdx);
        }

        then.read();

        endIdx = then.endIdx();
//
//        // another expression that should not be a conditional
//
//        nextIdx = then.endIdx() + 1; // next char after the then token
//        nextIdx = Reader.nextValidCharIndex(nextIdx, content);
//
//        nextToken = ContextVariableScanner.instance().findNext(startIdx, content());
//        if (nextToken.isEmpty()) {
//            throw new RuntimeException("Invalid syntax close to index " + startIdx);
//        }
    }

    @Override
    public Object evaluate(ContextHolder context) {

        var isTrueCondition = condition.evaluate(context);

        if (isTrueCondition) {
            result = (String) then.evaluate(context);
            return result;
        }

        if (isNull(orElse)) {

            // TODO: orElse
            result = "";
            return result;
        }

        result = (String) orElse.evaluate(context);

        return result;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "IfToken{" +
                "condition=" + condition +
                ", then=" + then +
                ", orElse=" + orElse +
                '}';
    }

    // TODO should be a member inner class?
    public static class ThenToken extends Token {

        private ContextVariableToken variable;

        public ThenToken(TokenInput input) {
            super(THEN, input);
        }

        /**
         * THEN token should have just a context variable to provide the final value.
         */
        @Override
        public void read() {
            // Next element after the token
            var startIdx = tokenEndIdx() + 1;

            startIdx = Reader.nextValidCharIndex(startIdx, content());

            Optional<Token> nextToken = ContextVariableScanner.instance().findNext(startIdx, content());
            if (nextToken.isEmpty()) {
                throw new RuntimeException("Invalid syntax close to index " + startIdx);
            }

            var token = nextToken.get();
            if (token instanceof ContextVariableToken contextVariableToken) {
                variable = contextVariableToken;
            }
            else {
                throw new UnexpectedTokenException(CONTEXT_VARIABLE, token.type(), startIdx);
            }

            variable.read();
            endIdx = variable.endIdx();
        }

        @Override
        public Object evaluate(ContextHolder context) {
            return variable.evaluate(context);
        }

        @Override
        public String toString() {
            return "ThenToken{" +
                    "variable=" + variable +
                    '}';
        }
    }
}
