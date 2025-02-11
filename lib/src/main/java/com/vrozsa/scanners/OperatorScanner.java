package com.vrozsa.scanners;

import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.operators.EqualsToken;
import com.vrozsa.tokens.operators.OperatorToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.EQUALS;

public class OperatorScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(EQUALS, EqualsToken::new)

//            NOT_EQUALS
//            GREATER_THAN
//            GREATER_THAN_EQUALS
//            LESS_THAN
//            LESS_THAN_EQUALS
    ));

    private static final OperatorScanner INSTANCE = new OperatorScanner();

    public static OperatorScanner instance() {
        return INSTANCE;
    }

    OperatorScanner() {
        super(tokensCreator);
    }

    @Override
    public Optional<OperatorToken> findNext(final int idx, final char[] content) {
        return (Optional<OperatorToken>) super.findNext(idx, content);
    }

    @Override
    protected Optional<? extends Token> createFallbackToken(String keyword, TokenInput tokenInput) {
        return Optional.empty();
    }
}
