package com.vrozsa.scanners;

import com.vrozsa.tokens.Label;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Map;
import java.util.Optional;

/**
 * Scanner for simple labels used in tokens expressions.
 */
public class LabelScanner extends AbstractTokenScanner {
    private static final LabelScanner INSTANCE = new LabelScanner();

    LabelScanner() {
        super(Map.of());
        // TODO: allow AbstractTokenScanner to receive a list of starting and middle characters so we can allow labels of just a-zA-Z
    }

    public static LabelScanner instance() {
        return INSTANCE;
    }

    @Override
    public Optional<Label> findNext(final int idx, final char[] content) {
        return (Optional<Label>) super.findNext(idx, content);
    }

    @Override
    protected Optional<? extends Token> createFallbackToken(TokenInput tokenInput) {
        return Optional.of(new Label(tokenInput));
    }
}
