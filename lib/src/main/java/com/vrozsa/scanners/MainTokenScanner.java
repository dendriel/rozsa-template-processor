package com.vrozsa.scanners;

import com.vrozsa.tokens.IfToken;
import com.vrozsa.tokens.SwitchToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.IF;
import static com.vrozsa.tokens.TokenType.SWITCH;

public class MainTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(IF, IfToken::new),
            Map.entry(SWITCH, SwitchToken::new)
//    FILTER,
//    UPPERCASE,
//    LOWERCASE
    ));

    private static final MainTokenScanner INSTANCE = new MainTokenScanner();

    public static MainTokenScanner instance() {
        return INSTANCE;
    }

    public MainTokenScanner() {
        super(tokensCreator);
    }
}
