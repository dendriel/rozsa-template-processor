package com.vrozsa.scanners;

import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.conditionals.IfToken;
import com.vrozsa.tokens.conditionals.SwitchToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;
import com.vrozsa.tokens.functions.LowercaseToken;
import com.vrozsa.tokens.functions.UppercaseToken;
import com.vrozsa.tokens.transformations.SetToken;
import com.vrozsa.tokens.transformations.SortToken;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.vrozsa.tokens.TokenType.*;

public class MainTokenScanner extends AbstractTokenScanner {
    private static final EnumMap<TokenType, Function<TokenInput, Token>> tokensCreator = new EnumMap<>(Map.ofEntries(
            Map.entry(IF, IfToken::new),
            Map.entry(SWITCH, SwitchToken::new),
            Map.entry(UPPERCASE, UppercaseToken::new),
            Map.entry(LOWERCASE, LowercaseToken::new),
            Map.entry(SORT, SortToken::new),
            Map.entry(SET, SetToken::new)
    ));

    private static final MainTokenScanner INSTANCE = new MainTokenScanner();

    public MainTokenScanner() {
        super(tokensCreator);
    }

    public static MainTokenScanner instance() {
        return INSTANCE;
    }
}
