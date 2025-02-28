package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.scanners.ConditionScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Condition;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import static java.util.Objects.nonNull;

public class OnToken extends AbstractToken {

    private Condition condition;

    public OnToken(TokenInput input) {
        super(TokenType.ON, input);
    }

    @Override
    public void read() {
        var startIdx = contentStartIdx();
        scanCondition(startIdx);

        endIdx = condition.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        var isTrueCondition = condition.evaluate(context);

        if (nonNull(isTrueCondition)) {
            return isTrueCondition;
        }

        return false;
    }

    public String evaluateAsLabel(ContextHolder context) {
        return condition.keyword();
    }

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
}
