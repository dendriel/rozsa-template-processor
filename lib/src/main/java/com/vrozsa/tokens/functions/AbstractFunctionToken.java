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

    private final boolean isMultiParam;

    protected List<Token> params;

    AbstractFunctionToken(TokenType type, TokenInput input) {
        this(type, input, false);
    }

    AbstractFunctionToken(TokenType type, TokenInput input, boolean isMultiParam) {
        super(type, input);
        this.isMultiParam = isMultiParam;
        params = new ArrayList<>();
    }

    @Override
    public void read() {
        // Next element after the token
        var startIdx = tokenEndIdx() + 1;
        startIdx = Reader.nextValidCharIndex(startIdx, content());

        var content = content();

        var nextChar = content[startIdx++];
        if (!startGroupCharChecker.match(nextChar)) {
            throw new UnexpectedCharacterException('(', nextChar, startIdx);
        }

        // Read the expected context variable.
        super.read(startIdx);
        params.add(super.variable);

        // Next valid char after the variable
        endIdx = Reader.nextValidCharIndex(endIdx + 1, content());
        nextChar = content[endIdx];

        if (varSeparatorCharChecker.match(nextChar) && isMultiParam) {
            readNextParams(endIdx, content);

            // Next valid char after the variable
            endIdx = Reader.nextValidCharIndex(endIdx, content());
            nextChar = content[endIdx];
        }

        if (!endGroupCharChecker.match(nextChar)) {
            throw new UnexpectedCharacterException(')', nextChar, endIdx);
        }
    }

    private void readNextParams(int startIdx, char[] content) {
        var nextIdx = startIdx;
        char nextChar;

        do {
            nextIdx = Reader.nextValidCharIndex(nextIdx + 1, content());
            super.read(nextIdx);
            params.add(super.variable);

            nextIdx = Reader.nextValidCharIndex(endIdx + 1, content());
            nextChar = content[nextIdx];
        } while (varSeparatorCharChecker.match(nextChar));

        endIdx = Reader.nextValidCharIndex(endIdx + 1, content());
    }
}
