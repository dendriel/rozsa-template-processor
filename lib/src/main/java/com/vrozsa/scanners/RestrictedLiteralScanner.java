package com.vrozsa.scanners;

import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.UnexpectedTokenException;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * This class ensures we won't read a literal when we have to read another type.
 */
public class RestrictedLiteralScanner extends LiteralScanner {

    private final TokenType expectedType;
    private final String expectedTypeText;

    public RestrictedLiteralScanner(TokenType expectedType) {
        this.expectedType = expectedType;
        expectedTypeText = null;
    }

    public RestrictedLiteralScanner(String expectedTypeText) {
        this.expectedTypeText = expectedTypeText;
        expectedType = null;
    }

    @Override
    protected Optional<Token> createLiteralToken(TokenInput tokenInput) {
        if (!isNull(expectedType)) {
            throw new UnexpectedTokenException(expectedType, TokenType.LITERAL, tokenInput.startIdx());
        }
        else {
            throw new InvalidSyntaxException(String.format("Expecting a %s", expectedTypeText), tokenInput.startIdx());
        }
    }
}
