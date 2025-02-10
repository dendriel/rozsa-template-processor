package com.vrozsa;

import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Optional;

abstract class AbstractTokenScanner {
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
            new CharacterSingle('_'),
            new CharacterSingle('-')
    );


    /**
     * Look for the next token.
     * @param idx starting index to scan in the content.
     * @param content content to be scanned.
     * @return the next token if found.
     */
    public Optional<Token> findNext(final int idx, final char[] content) {
        var startIdx = Reader.nextValidCharIndex(idx, content);

        var nextIdx = startIdx;

        var nextChar = content[nextIdx++];
        if (!startingCharsChecker.match(nextChar)) {
            throw new InvalidSyntaxException(String.format("Invalid starting character with value %s", content[startIdx]), startIdx);
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
        // -1 for the stop character
        var endIdx = nextIdx - 1;

        if (matchAnyToken(keyword)) {
            return createToken(keyword, startIdx, endIdx, content);
        }

        return Optional.of(new ContextVariableToken(keyword, new TokenInput(startIdx, endIdx, content)));
    }

    protected abstract boolean matchAnyToken(String name);

    protected abstract Optional<Token> createToken(String name, int startIdx, int endIdx, char[] content);
}
