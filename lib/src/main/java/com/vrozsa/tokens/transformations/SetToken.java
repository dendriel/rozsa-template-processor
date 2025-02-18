package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.exceptions.UnexpectedTokenException;
import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.scanners.ExpressibleValueScanner;
import com.vrozsa.scanners.MainTokenScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Label;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Optional;

public class SetToken extends AbstractToken {

    private Token targetValue;

    private AsToken asToken;

    public SetToken(TokenInput input) {
        super(TokenType.SET, input);
    }

    @Override
    public void read() {
        var startIdx = contentStartIdx();

        var optToken = ExpressibleValueScanner.findNext(startIdx, content());
        if (optToken.isEmpty()) {
            throw new InvalidSyntaxException("A variable, function or expression was expected", startIdx);
        }

        targetValue = optToken.get();
        targetValue.read();

        startIdx = Reader.nextValidCharIndex(targetValue.endIdx() + 1, content());

        var optAsToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optAsToken.isEmpty()) {
            throw new InvalidSyntaxException("A AS token was expected", startIdx);
        }

        if (optAsToken.get().type() != TokenType.AS) {
            throw new UnexpectedTokenException(TokenType.AS, optAsToken.get().type(), startIdx);
        }

        asToken = (AsToken) optAsToken.get();
        asToken.read();

        endIdx = asToken.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        Object value = targetValue.evaluate(context);
        String key = asToken.evaluate(context);

        context.addCustom(key, value);

        // Return null because we don't want anything to be rendered for this token.
        return null;
    }
}
