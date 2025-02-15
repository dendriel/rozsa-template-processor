package com.vrozsa.scanners;

import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.conditionals.ElseToken;
import com.vrozsa.tokens.conditionals.ThenToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.vrozsa.tokens.Expression.END_BRACKET;
import static com.vrozsa.tokens.TokenType.ELSE;
import static com.vrozsa.tokens.TokenType.THEN;

public class AuxiliaryTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(THEN, ThenToken::new),
            Map.entry(ELSE, ElseToken::new)
    ));

    private static final AuxiliaryTokenScanner INSTANCE = new AuxiliaryTokenScanner();

    public static AuxiliaryTokenScanner instance() {
        return INSTANCE;
    }

    public AuxiliaryTokenScanner() {
        super(tokensCreator);
    }

    @Override
    protected Optional<Token> handleInvalidStartingChar(int idx, char[] content) {
        // If it is the end of the expression, there is no extra auxiliary token.
        if (content[idx] == END_BRACKET) {
            return Optional.empty();
        }

        return super.handleInvalidStartingChar(idx, content);
    }
}
