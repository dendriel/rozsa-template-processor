package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.tokens.Condition;
import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.IfToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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

            return null;
        }

        var token = super.findNext(idx, content);
        if (token.isEmpty()) {
            return Optional.empty();
        }

        var targetToken = token.get();
        targetToken.read();

        return Optional.of(new Condition(targetToken));
    }

    @Override
    protected boolean matchAnyToken(String name) {
        return true;
    }

    @Override
    protected Optional<Token> createToken(String name, int startIdx, int endIdx, char[] content) {
        return Optional.of(new ContextVariableToken(name, new TokenInput(startIdx, endIdx, content)));
    }
}
