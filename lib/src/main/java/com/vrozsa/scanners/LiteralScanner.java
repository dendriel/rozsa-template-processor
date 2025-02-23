package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.CharacterSingle;
import com.vrozsa.tokens.Literal;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Optional;

public class LiteralScanner {

    private static final CharacterChecker literalStartingCharsChecker = CharacterChecker.of(
            new CharacterRange('0', '9'),
            new CharacterSingle('"')
    );

    private static final CharacterChecker literalTextMarkCharChecker = CharacterChecker.of(
            new CharacterSingle('"')
    );

    private static final CharacterChecker numberMiddleCharsChecker = CharacterChecker.of(
            new CharacterRange('0', '9'),
            new CharacterSingle('.'),
            new CharacterSingle('E')
    );

    public boolean isLiteralStartingCharacter(char startingChar) {
        return literalStartingCharsChecker.match(startingChar);
    }

    public Optional<Token> readNextLiteral(final int startIdx, final char startChar, final char[] content) {

        var isTextLiteral = literalTextMarkCharChecker.match(startChar);
        if (isTextLiteral) {
            return readTextLiteral(startIdx, startChar, content);
        }

        return readNumberLiteral(startIdx, startChar, content);
    }

    public Optional<Token> readNumberLiteral(final int startIdx, final char startChar, final char[] content) {
        var keywordBuilder = new StringBuilder();
        keywordBuilder.append(startChar);

        char nextChar;
        var nextIdx = startIdx + 1;

        while (nextIdx < content.length) {

            nextChar = content[nextIdx];
            if (!numberMiddleCharsChecker.match(nextChar)) {
                break;
            }

            keywordBuilder.append(nextChar);
            nextIdx++;
        }

        var keyword = keywordBuilder.toString();
        var endIdx = nextIdx - 1;

        return createLiteralToken(new TokenInput(keyword, startIdx, endIdx, content));
    }

    private Optional<Token> readTextLiteral(final int startIdx, final char startChar, final char[] content) {
        var keywordBuilder = new StringBuilder();
        keywordBuilder.append(startChar);

        char nextChar;
        var nextIdx = startIdx + 1;

        while (nextIdx < content.length) {

            nextChar = content[nextIdx];

            keywordBuilder.append(nextChar);
            nextIdx++;
            if (literalTextMarkCharChecker.match(nextChar)) {
                break;
            }
        }

        var keyword = keywordBuilder.toString();
        var endIdx = nextIdx - 1;

        return createLiteralToken(new TokenInput(keyword, startIdx, endIdx, content));
    }


    protected Optional<Token> createLiteralToken(TokenInput tokenInput) {
        return Optional.of(new Literal(tokenInput));
    }
}
