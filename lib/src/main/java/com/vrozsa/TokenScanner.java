package com.vrozsa;

import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Optional;

abstract class TokenScanner {
    private static final CharacterChecker startingCharsChecker = CharacterChecker.of(
            new CharacterRange('A', 'Z'),
            new CharacterRange('a', 'z'),
            new CharacterRange('0', '9')
    );

    private static final CharacterChecker middleCharsChecker = CharacterChecker.of(
            new CharacterRange('A', 'Z'), // TODO: use classes to define default ranges
            new CharacterRange('a', 'z'),
            new CharacterRange('0', '9'),
            new CharacterSingle('.'),
            new CharacterSingle('_')
    );


    // look for the next valid token.
    public Optional<Token> findNext(final int idx, final char[] content) {
        var startIdx = Reader.nextValidCharIndex(idx, content);

        var nextIdx = startIdx;

        var nextChar = content[nextIdx++];
        if (!startingCharsChecker.match(nextChar)) {
            var msg = "Invalid starting character at index: " + (nextIdx-1) + " value: \"" + content[nextIdx-1] + "\"";
            throw new RuntimeException(msg); // todo: do error handling
        }

        var keywordBuilder = new StringBuilder();
        keywordBuilder.append(nextChar);

        while (nextIdx < content.length) {

            nextChar = content[nextIdx];
            if (!middleCharsChecker.match(nextChar)) {
                break;
            }

            keywordBuilder.append(nextChar);
            nextIdx++;
        }

        var keyword = keywordBuilder.toString();
        var endIdx = nextIdx - 1; // -1 for the stop character

        if (anyMatch(keyword)) {
            return create(keyword, startIdx, endIdx, content);
        }

        return Optional.of(new ContextVariableToken(keyword, new TokenInput(startIdx, endIdx, content)));
    }

    protected abstract boolean anyMatch(String name);

    protected abstract Optional<Token> create(String name, int startIdx, int endIdx, char[] content);
}
