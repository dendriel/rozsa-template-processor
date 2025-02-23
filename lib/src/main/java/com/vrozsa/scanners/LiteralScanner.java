package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.CharacterSingle;
import com.vrozsa.tokens.Literal;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;

import java.util.Locale;
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

    public static boolean isBooleanLiteral(String keyword) {
        var lowerKeyword = keyword.toLowerCase(Locale.ROOT);
        return Boolean.TRUE.toString().equals(lowerKeyword) || Boolean.FALSE.toString().equals(lowerKeyword);
    }

    public Optional<Token> readNextLiteral(final int startIdx, final char startChar, final char[] content) {

        var literalBuilder = new StringBuilder();
        literalBuilder.append(startChar);

        int endIdx;

        var isTextLiteral = literalTextMarkCharChecker.match(startChar);
        if (isTextLiteral) {
            endIdx = readTextLiteral(literalBuilder, startIdx, content);
        }
        else {
            endIdx = readNumberLiteral(literalBuilder, startIdx, content);
        }

        var keyword = literalBuilder.toString();
        return createLiteralToken(new TokenInput(keyword, startIdx, endIdx, content));
    }

    private static int readNumberLiteral(StringBuilder literalBuilder, final int startIdx, final char[] content) {
        char nextChar;
        var nextIdx = startIdx + 1;

        while (nextIdx < content.length) {

            nextChar = content[nextIdx];
            if (!numberMiddleCharsChecker.match(nextChar)) {
                break;
            }

            literalBuilder.append(nextChar);
            nextIdx++;
        }

        return nextIdx - 1;
    }

    private static int readTextLiteral(StringBuilder literalBuilder, final int startIdx, final char[] content) {
        char nextChar;
        var nextIdx = startIdx + 1;

        while (nextIdx < content.length) {
            nextChar = content[nextIdx];
            literalBuilder.append(nextChar);
            nextIdx++;

            if (literalTextMarkCharChecker.match(nextChar)) {
                break;
            }
        }

        return nextIdx - 1;
    }

    protected Optional<Token> createLiteralToken(TokenInput tokenInput) {
        return Optional.of(new Literal(tokenInput));
    }
}
