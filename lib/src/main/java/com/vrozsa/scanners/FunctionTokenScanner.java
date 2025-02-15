package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.functions.LowercaseToken;
import com.vrozsa.tokens.functions.UppercaseToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.LOWERCASE;
import static com.vrozsa.tokens.TokenType.UPPERCASE;

public class FunctionTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(UPPERCASE, UppercaseToken::new),
            Map.entry(LOWERCASE, LowercaseToken::new)
    ));

    private static final FunctionTokenScanner INSTANCE = new FunctionTokenScanner();

    public static FunctionTokenScanner instance() {
        return INSTANCE;
    }

    FunctionTokenScanner() {
        super(tokensCreator);
    }
}
