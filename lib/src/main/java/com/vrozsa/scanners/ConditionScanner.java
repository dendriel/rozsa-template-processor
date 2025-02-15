package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Expression;
import com.vrozsa.Reader;
import com.vrozsa.tokens.Condition;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Token;

import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;


public class ConditionScanner extends AbstractTokenScanner {

    private static final CharacterChecker groupCharChecker = CharacterChecker.of(
            new CharacterSingle('(')
    );

    private static final ConditionScanner INSTANCE = new ConditionScanner();

    public static ConditionScanner instance() {
        return INSTANCE;
    }

    ConditionScanner() {
        super(Map.of());
    }

    @Override
    public Optional<Condition> findNext(final int idx, final char[] content) {
        var startIdx = Reader.nextValidCharIndex(idx, content);

//        var nextChar = content[startIdx];
//        if (groupCharChecker.match(nextChar)) {
//            var token = super.findNext(startIdx + 1, content);
//            // TODO
//            return null;
//        }

        Token targetToken = null;

        if (ExpressionScanner.isNextTokenAnExpression(startIdx, content)) {
            var expressionStartIdx = startIdx + 1;
            targetToken = new Expression(expressionStartIdx, content);
        }
        else {
            var token = FunctionTokenScanner.instance().findNext(idx, content);
            if (token.isPresent()) {
                targetToken = token.get();
            }
        }

        if (isNull(targetToken)) {
            return Optional.empty();
        }

        targetToken.read();
        var nextIdx = targetToken.endIdx() + 1;

        // Operator: equals, not equals, >, >= etc
        var optOperator = OperatorScanner.instance().findNext(nextIdx, content);

        if (optOperator.isEmpty()) {
            // return plain condition.
            return Optional.of(new Condition(targetToken));
        }

        var operator = optOperator.get();
        operator.read();

        return Optional.of(new Condition(targetToken, operator));
    }
}
