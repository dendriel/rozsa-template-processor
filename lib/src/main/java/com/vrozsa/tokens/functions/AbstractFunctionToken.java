package com.vrozsa.tokens.functions;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.UnexpectedCharacterException;
import com.vrozsa.tokens.ExpressibleValueCompanionToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractFunctionToken extends ExpressibleValueCompanionToken {

    private static final CharacterChecker startGroupCharChecker = CharacterChecker.of(
            new CharacterSingle('(')
    );

    private static final CharacterChecker endGroupCharChecker = CharacterChecker.of(
            new CharacterSingle(')')
    );

    private static final CharacterChecker varSeparatorCharChecker = CharacterChecker.of(
            new CharacterSingle(',')
    );

    // When using multiple parameters, consume from this field.
    protected List<Token> params;

    AbstractFunctionToken(TokenType type, TokenInput input) {
        super(type, input);
        params = new ArrayList<>();
    }

    @Override
    public void read() {
        // Next element after the token
        var startIdx = tokenEndIdx() + 1;
        startIdx = Reader.nextValidCharIndex(startIdx, content());

        var content = content();

        var nextChar = content[startIdx];
        if (!startGroupCharChecker.match(nextChar)) {
            throw new UnexpectedCharacterException('(', nextChar, startIdx);
        }

        // Read the expected context variable.
        readNextParams(startIdx, content);

        // Next valid char after the variable
        endIdx = Reader.nextValidCharIndex(endIdx + 1, content());
        nextChar = content[endIdx];

        if (!endGroupCharChecker.match(nextChar)) {
            throw new UnexpectedCharacterException(')', nextChar, endIdx);
        }
    }

    private void readNextParams(final int startIdx, final char[] content) {
        var nextIdx = startIdx;
        char nextChar;

        do {
            // +1 skips the separator '(' in the first run; and the comma ',' in the following runs
            nextIdx = Reader.nextValidCharIndex(nextIdx + 1, content());
            super.read(nextIdx);
            params.add(super.variable);

            nextIdx = Reader.nextValidCharIndex(endIdx + 1, content());
            nextChar = content[nextIdx];
        } while (varSeparatorCharChecker.match(nextChar));
    }
}
