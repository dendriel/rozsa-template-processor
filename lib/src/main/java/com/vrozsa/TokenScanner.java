package com.vrozsa;

import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Optional;

abstract class TokenScanner {
    private static final String EMPTY_STRING = "";

    private static final CharacterChecker startingCharsChecker = CharacterChecker.of(
            new CharacterRange(65, 90), // A-Z
            new CharacterRange(97, 122) // a-z
    );

    private static final CharacterChecker middleCharsChecker = CharacterChecker.of(
            new CharacterRange(65, 90), // A-Z
            new CharacterRange(97, 122), // a-z
            new CharacterSingle(46) // .
    );

    private static final CharacterChecker separatorCharsChecker = CharacterChecker.of(
            new CharacterSingle(32), // space
            new CharacterSingle(9) // tab
    );

    private int nextNoSeparatorCharacterIdx(final int idx, final char[] content) {
        var nextIdx = idx;

        while (nextIdx < content.length) {
            var nextChar = content[nextIdx];
            if (!separatorCharsChecker.isValid(nextChar)) {
                break;
            }
            nextIdx++;
        }

        return nextIdx;
    }

    // look for the next valid token.
    public Optional<Token> findNext(final int idx, final char[] content) {
        var nextIdx = nextNoSeparatorCharacterIdx(idx, content);

        var keywordBuilder = new StringBuilder();

        var nextChar = content[nextIdx++];
        if (!startingCharsChecker.isValid(nextChar)) {
            var msg = "Invalid starting character at index: " + (nextIdx-1) + " value: \"" + content[nextIdx-1] + "\"";
            throw new RuntimeException(msg); // todo: do error handling
        }

        keywordBuilder.append(nextChar);

        while (nextIdx < content.length) {

            nextChar = content[nextIdx];
            if (!middleCharsChecker.isValid(nextChar)) {
                break;
            }

            keywordBuilder.append(nextChar);
            nextIdx++;
        }

        var keyword = keywordBuilder.toString();
        var startIdx = nextIdx;
        var endIdx = nextIdx - 1; // -1 for the stop character;

        if (anyMatch(keyword)) {
            return create(keyword, startIdx, endIdx, content);
        }

        return Optional.of(new ContextVariableToken(keyword, new TokenInput(startIdx, endIdx, content)));
    }

    protected abstract boolean anyMatch(String name);

    protected abstract Optional<Token> create(String name, int startIdx, int endIdx, char[] content);
}
