package com.vrozsa.tokens;

import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.ContextHolder;
import com.vrozsa.scanners.ConditionScanner;
import com.vrozsa.scanners.ContextVariableScanner;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.UnexpectedTokenException;

import java.util.Optional;

import static com.vrozsa.tokens.TokenType.CONTEXT_VARIABLE;
import static com.vrozsa.tokens.TokenType.ELSE;
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

        // TODO: First can be an expression, a conditional or a context variable

        // The first token is a condition
        var conditionToken = ConditionScanner.instance().findNext(startIdx, content);
        if (conditionToken.isEmpty()) {
            throw new RuntimeException("Invalid syntax close to index " + startIdx);
        }

        condition = conditionToken.get();

        // next char after the condition
        var nextIdx = condition.endIdx() + 1;
        nextIdx = Reader.nextValidCharIndex(nextIdx, content);

        var thenToken = AuxiliaryTokenScanner.instance().findNext(nextIdx, content);
        if (thenToken.isEmpty()) {
            throw new RuntimeException("Couldn't find the next token from if");
        }

        // Next should be a mandatory THEN token.
        then = thenToken.get();
        if (!THEN.equals(then.type())) {
            throw new UnexpectedTokenException(THEN, then.type(), nextIdx);
        }

        then.read();
        endIdx = then.endIdx();

        nextIdx = then.endIdx() + 1;
        nextIdx = Reader.nextValidCharIndex(nextIdx, content);

        // Scan for optional ELSE token.
        var elseToken = AuxiliaryTokenScanner.instance().findNext(nextIdx, content);
        if (elseToken.isEmpty()) {
            return;
        }

        orElse = elseToken.get();
        if (!ELSE.equals(orElse.type())) {
            throw new UnexpectedTokenException(ELSE, then.type(), nextIdx);
        }

        orElse.read();
        endIdx = orElse.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {

        var isTrueCondition = (Boolean) condition.evaluate(context);

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
}
