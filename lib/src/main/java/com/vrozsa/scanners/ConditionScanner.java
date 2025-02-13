package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Reader;
import com.vrozsa.tokens.Condition;
import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Map;
import java.util.Optional;

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

        var nextChar = content[startIdx];
        if (groupCharChecker.match(nextChar)) {
            var token = super.findNext(startIdx + 1, content);
            // TODO
            return null;
        }

        var token = super.findNext(idx, content);
        if (token.isEmpty()) {
            return Optional.empty();
        }

        var targetToken = token.get();
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

    @Override
    protected boolean matchAnyToken(String name) {
        return true;
    }

    @Override
    protected Optional<Token> createToken(String keyword, int startIdx, int endIdx, char[] content) {
        return Optional.of(new ContextVariableToken(new TokenInput(keyword, startIdx, endIdx, content)));
    }
}
