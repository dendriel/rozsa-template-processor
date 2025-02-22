package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.tokens.ContextVariableToken;
import com.vrozsa.tokens.Literal;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

abstract class AbstractTokenScanner {
    private static final CharacterChecker startingCharsChecker = CharacterChecker.of(
            new CharacterRange('A', 'Z'),
            new CharacterRange('a', 'z'),
//            new CharacterRange('0', '9'),
            new CharacterSingle('<'),
            new CharacterSingle('='),
            new CharacterSingle('>'),
            new CharacterSingle('!'),
            new CharacterSingle('"')
    );

    private static final CharacterChecker literalStartingCharsChecker = CharacterChecker.of(
            new CharacterRange('0', '9'),
            new CharacterSingle('"')
    );

    private static final CharacterChecker literalTextMarkCharChecker = CharacterChecker.of(
            new CharacterSingle('"')
    );


    private static final CharacterChecker middleCharsChecker = CharacterChecker.of(
            new CharacterRange('A', 'Z'), // TODO: use classes to define default ranges
            new CharacterRange('a', 'z'),
            new CharacterRange('0', '9'),
            new CharacterSingle('.'),
            new CharacterSingle('_'),
            new CharacterSingle('-'),
            new CharacterSingle('=')
    );

    private final Map<TokenType, Function<TokenInput, Token>> tokensCreator;

    AbstractTokenScanner(Map<TokenType, Function<TokenInput, Token>> tokensCreator) {
        this.tokensCreator = tokensCreator;
    }

    /**
     * Look for the next token.
     * @param idx starting index to scan in the content.
     * @param content content to be scanned.
     * @return the next token if found.
     */
    public Optional<? extends Token> findNext(final int idx, final char[] content) {
        var startIdx = Reader.nextValidCharIndex(idx, content);

        var nextIdx = startIdx;

        var nextChar = content[nextIdx++];
        if (!startingCharsChecker.match(nextChar) && !literalStartingCharsChecker.match(nextChar)) {
            return handleInvalidStartingChar(startIdx, content);
        }

        var isLiteral = literalStartingCharsChecker.match(nextChar);

        var keywordBuilder = new StringBuilder();
        keywordBuilder.append(nextChar);

        while (nextIdx < content.length) {

            nextChar = content[nextIdx];

            // Include the text mark char only if we are scanning a literal Text. Otherwise we may scan a stand-alone
            // context variable that happens to be followed by the quote as in `"$ctx_variable"` and it would be read as
            // `ctx_variable"`
            if (literalTextMarkCharChecker.match(nextChar) && isLiteral) {
                keywordBuilder.append(nextChar);
                nextIdx++;
                break;
            }

            if (!middleCharsChecker.match(nextChar)) {
                break;
            }

            keywordBuilder.append(nextChar);
            nextIdx++;
        }

        var keyword = keywordBuilder.toString();
        // -1 for the stop character
        var endIdx = nextIdx - 1;

        var tokenInput = new TokenInput(keyword, startIdx, endIdx, content);

        if (matchAnyToken(keyword)) {
            return createToken(keyword, startIdx, endIdx, content);
        }

        if (literalStartingCharsChecker.match(keyword.charAt(0))) {
            return createLiteralToken(tokenInput);
        }

        return createFallbackToken(tokenInput);
    }

    // Here it has a return to allow subclasses to return something instead of throwing.
    protected Optional<Token> handleInvalidStartingChar(int idx, char[] content) {
        throw new InvalidSyntaxException(String.format("Invalid starting character with value %s", content[idx]), idx);
    }

    protected Optional<Token> createLiteralToken(TokenInput tokenInput) {
        return Optional.of(new Literal(tokenInput));
    }

    protected Optional<Token> createFallbackToken(TokenInput tokenInput) {
        return Optional.of(new ContextVariableToken(tokenInput));
    }

    protected boolean matchAnyToken(String name) {
        return tokensCreator.keySet().stream()
                .anyMatch(k -> k.match(name));
    }

    protected Optional<Token> createToken(String name, int startIdx, int endIdx, char[] content) {
        for (var tokenType : tokensCreator.keySet()) {
            if (tokenType.match(name)) {
                var newToken = tokensCreator.get(tokenType).apply(new TokenInput(name, startIdx, endIdx, content));
                return Optional.of(newToken);
            }
        }

        return Optional.empty();
    }
}
