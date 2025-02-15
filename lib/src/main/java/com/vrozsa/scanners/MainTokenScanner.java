package com.vrozsa.scanners;

import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.conditionals.IfToken;
import com.vrozsa.tokens.conditionals.SwitchToken;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.functions.LowercaseToken;
import com.vrozsa.tokens.functions.UppercaseToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.IF;
import static com.vrozsa.tokens.TokenType.LOWERCASE;
import static com.vrozsa.tokens.TokenType.SWITCH;
import static com.vrozsa.tokens.TokenType.UPPERCASE;

public class MainTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(IF, IfToken::new),
            Map.entry(SWITCH, SwitchToken::new),
            Map.entry(UPPERCASE, UppercaseToken::new),
            Map.entry(LOWERCASE, LowercaseToken::new)
//    FILTER,
    ));

    private static final MainTokenScanner INSTANCE = new MainTokenScanner();

    public static MainTokenScanner instance() {
        return INSTANCE;
    }

    public MainTokenScanner() {
        super(tokensCreator);
    }
}
