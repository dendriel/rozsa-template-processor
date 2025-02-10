package com.vrozsa;

import com.vrozsa.tokens.IfToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.THEN;

public class AuxiliaryTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(THEN, IfToken.ThenToken::new)
    ));

    private static final AuxiliaryTokenScanner INSTANCE = new AuxiliaryTokenScanner();

    public static AuxiliaryTokenScanner instance() {
        return INSTANCE;
    }

    public AuxiliaryTokenScanner() {
        super(tokensCreator);
    }
}
