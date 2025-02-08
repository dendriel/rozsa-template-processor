package com.vrozsa;

import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.StartingToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Optional;

public class TokenScanner {
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


    // look for the next valid token.
    public static Optional<Token> findNext(final int idx, final String content) {
        var nextIdx = idx;

        var keywordBuilder = new StringBuilder();

        var nextChar = content.charAt(nextIdx++);
        if (!startingCharsChecker.isValid(nextChar)) {
            var msg = "Invalid starting character at index: " + (nextIdx-1);
            throw new RuntimeException(msg); // todo: do error handling
        }

        keywordBuilder.append(nextChar);

        while (nextIdx < content.length()) {

            nextChar = content.charAt(nextIdx);
            if (!middleCharsChecker.isValid(nextChar)) {
                break;
            }

            keywordBuilder.append(nextChar);
            nextIdx++;
        }

        var keyword = keywordBuilder.toString();
        var startIdx = idx;
        var endIdx = nextIdx - 1; // -1 for the stop character;

        if (StartingToken.anyMatch(keyword)) {
            return StartingToken.create(keyword, startIdx, endIdx, content);
        }

        return Optional.of(new ContextVariableToken(keyword, new TokenInput(startIdx, endIdx, content)));
    }
}
