package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Reader;
import com.vrozsa.tokens.Condition;
import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.functions.UppercaseToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.UPPERCASE;

public class ConditionScanner extends AbstractTokenScanner {

    private static final CharacterChecker groupCharChecker = CharacterChecker.of(
            new CharacterSingle('(')
    );

    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(UPPERCASE, UppercaseToken::new)
//            Map.entry(LOWERCASE, ElseToken::new)
    ));

    private static final ConditionScanner INSTANCE = new ConditionScanner();

    public static ConditionScanner instance() {
        return INSTANCE;
    }

    ConditionScanner() {
        super(tokensCreator);
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
}
