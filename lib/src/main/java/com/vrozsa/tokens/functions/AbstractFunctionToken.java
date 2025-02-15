package com.vrozsa.tokens.functions;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.UnexpectedCharacterException;
import com.vrozsa.tokens.ContextVariableCompanionToken;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

abstract class AbstractFunctionToken extends ContextVariableCompanionToken {

    private static final CharacterChecker startGroupCharChecker = CharacterChecker.of(
            new CharacterSingle('(')
    );

    private static final CharacterChecker endGroupCharChecker = CharacterChecker.of(
            new CharacterSingle(')')
    );

    AbstractFunctionToken(TokenType type, TokenInput input) {
        super(type, input);
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

        // Next valid char after the variable
        endIdx = Reader.nextValidCharIndex(endIdx + 1, content());

        nextChar = content[endIdx];
        if (!endGroupCharChecker.match(nextChar)) {
            throw new UnexpectedCharacterException(')', nextChar, endIdx);
        }

//        startIdx = Reader.nextValidCharIndex(startIdx, content);
//
//        var nextToken = ContextVariableScanner.instance().findNext(startIdx, content());
//        if (nextToken.isEmpty()) {
//            throw new RuntimeException("Invalid syntax close to index " + startIdx);
//        }
//
//        var token = nextToken.get();
//        if (token instanceof ContextVariableToken contextVariableToken) {
//            variable = contextVariableToken;
//        }
//        else {
//            throw new UnexpectedTokenException(CONTEXT_VARIABLE, token.type(), startIdx);
//        }
//
//        variable.read();
//        endIdx = variable.endIdx();

    }
}
