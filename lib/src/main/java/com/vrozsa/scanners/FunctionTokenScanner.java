package com.vrozsa.scanners;

import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.functions.IsAbsentToken;
import com.vrozsa.tokens.functions.IsPresentToken;
import com.vrozsa.tokens.functions.LowercaseToken;
import com.vrozsa.tokens.functions.UppercaseToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.IS_ABSENT;
import static com.vrozsa.tokens.TokenType.IS_PRESENT;
import static com.vrozsa.tokens.TokenType.LOWERCASE;
import static com.vrozsa.tokens.TokenType.UPPERCASE;

public class FunctionTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, AbstractToken>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(UPPERCASE, UppercaseToken::new),
            Map.entry(LOWERCASE, LowercaseToken::new),
            Map.entry(IS_PRESENT, IsPresentToken::new),
            Map.entry(IS_ABSENT, IsAbsentToken::new)
    ));

    private static final FunctionTokenScanner INSTANCE = new FunctionTokenScanner();

    public static FunctionTokenScanner instance() {
        return INSTANCE;
    }

    FunctionTokenScanner() {
        super(tokensCreator);
    }
}
