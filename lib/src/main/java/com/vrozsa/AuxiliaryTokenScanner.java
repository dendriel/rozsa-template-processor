package com.vrozsa;

import com.vrozsa.tokens.IfToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.THEN;

public class AuxiliaryTokenScanner extends AbstractTokenScanner {
    private static final AuxiliaryTokenScanner INSTANCE = new AuxiliaryTokenScanner();

    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(THEN, IfToken.ThenToken::new)
    ));

    public static AuxiliaryTokenScanner instance() {
        return INSTANCE;
    }

    @Override
    protected boolean matchAnyToken(String name) {
        return tokensCreator.keySet().stream()
                .anyMatch(k -> k.match(name));
    }

    @Override
    public Optional<Token> createToken(String name, int startIdx, int endIdx, char[] content) {
        for (var tokenType : tokensCreator.keySet()) {
            if (tokenType.match(name)) {
                var newToken = tokensCreator.get(tokenType).apply(new TokenInput(startIdx, endIdx, content));
                return Optional.of(newToken);
            }
        }

        return Optional.empty();
    }
}
