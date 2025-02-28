package com.vrozsa.scanners;

import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.UnexpectedTokenException;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.operators.EqualsToken;
import com.vrozsa.tokens.operators.GreaterThanToken;
import com.vrozsa.tokens.operators.GreaterThanEqualsToken;
import com.vrozsa.tokens.operators.LessThanToken;
import com.vrozsa.tokens.operators.LessThanEqualsToken;
import com.vrozsa.tokens.operators.NotEqualsToken;
import com.vrozsa.tokens.operators.AbstractOperatorToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.*;

public class OperatorScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(EQUALS, EqualsToken::new),
            Map.entry(NOT_EQUALS, NotEqualsToken::new),
            Map.entry(GREATER_THAN, GreaterThanToken::new),
            Map.entry(GREATER_THAN_EQUALS, GreaterThanEqualsToken::new),
            Map.entry(LESS_THAN, LessThanToken::new),
            Map.entry(LESS_THAN_EQUALS, LessThanEqualsToken::new)
    ));

    private static final OperatorScanner INSTANCE = new OperatorScanner();

    public static OperatorScanner instance() {
        return INSTANCE;
    }

    OperatorScanner() {
        super(tokensCreator, new RestrictedLiteralScanner("OPERATOR"));
    }

    @Override
    public Optional<AbstractOperatorToken> findNext(final int idx, final char[] content) {
        return (Optional<AbstractOperatorToken>) super.findNext(idx, content);
    }

    @Override
    protected Optional<Token> handleInvalidStartingChar(int idx, char[] content) {
        // Return empty if there is no operator. Maybe the first token provides the conditional expression itself.
        return Optional.empty();
    }

    @Override
    protected Optional<Token> createFallbackToken(TokenInput tokenInput) {
        return Optional.empty();
    }
}
