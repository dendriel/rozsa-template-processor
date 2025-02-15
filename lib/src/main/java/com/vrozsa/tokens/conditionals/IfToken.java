package com.vrozsa.tokens.conditionals;

import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.ContextHolder;
import com.vrozsa.scanners.ConditionScanner;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.UnexpectedTokenException;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Condition;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static com.vrozsa.tokens.TokenType.ELSE;
import static com.vrozsa.tokens.TokenType.THEN;
import static java.util.Objects.isNull;

public class IfToken extends AbstractToken {
    private Condition condition;
    private Token then;
    private Token orElse;

    public IfToken(TokenInput input) {
        super(TokenType.IF, input);
    }

    @Override
    public void read() {
        var content = content();

        // Next valid char after the IF token
        var startIdx = tokenEndIdx() + 1;
        startIdx = Reader.nextValidCharIndex(startIdx, content);

        var nextIdx = scanCondition(startIdx);

        nextIdx = scanThenToken(nextIdx);

        scanElseToken(nextIdx);
    }

    // TODO: The condition can be an expression, a conditional or a context variable
    /**
     * Scan the condition.
     * @param startIdx start index in the content to perform the scan.
     * @return the next index after the condition.
     */
    private int scanCondition(int startIdx) {
        // The first token is a condition
        var conditionToken = ConditionScanner.instance().findNext(startIdx, content());
        if (conditionToken.isEmpty()) {
            throw new RuntimeException("Invalid syntax close to index " + startIdx);
        }

        condition = conditionToken.get();

        // next char after the condition
        var nextIdx = condition.endIdx() + 1;
        return Reader.nextValidCharIndex(nextIdx, content());
    }

    /**
     * Scans the mandatory THEN token.
     * @param startIdx start index in the content to perform the scan.
     * @return the next index after the condition.
     */
    private int scanThenToken(int startIdx) {
        var thenToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (thenToken.isEmpty()) {
            throw new RuntimeException("Couldn't find the next token from if");
        }

        // Next should be a mandatory THEN token.
        then = thenToken.get();
        if (!THEN.equals(then.type())) {
            throw new UnexpectedTokenException(THEN, then.type(), startIdx);
        }

        then.read();
        endIdx = then.endIdx();

        // next char after then expression
        var nextIdx = then.endIdx() + 1;
        return Reader.nextValidCharIndex(nextIdx, content());
    }

    /**
     * Scan for the optional ELSE token.
     * @param startIdx start index in the content to perform the scan.
     */
    private void scanElseToken(int startIdx) {
        var elseToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (elseToken.isEmpty()) {
            return;
        }

        orElse = elseToken.get();
        if (!ELSE.equals(orElse.type())) {
            throw new UnexpectedTokenException(ELSE, then.type(), startIdx);
        }

        orElse.read();
        endIdx = orElse.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {

        var isTrueCondition = condition.evaluate(context);

        if (isTrueCondition) {
            result = then.evaluate(context);
            return result;
        }

        if (isNull(orElse)) {
            // set default result to empty str otherwise it will print 'null'
            result = "";
            return result;
        }

        result = orElse.evaluate(context);
        return result;
    }

    @Override
    public String result() {
        return String.valueOf(result);
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
