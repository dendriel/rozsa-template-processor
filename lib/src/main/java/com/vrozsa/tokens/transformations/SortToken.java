package com.vrozsa.tokens.transformations;

import com.vrozsa.ContextHolder;
import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.scanners.ExpressibleValueScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Optional;

public class SortToken extends AbstractToken {
    private Token setVarToken;
    private Token asToken;
    private Token onToken;
    private Token sortOrderToken;

    public SortToken(TokenInput input) {
        super(TokenType.SORT, input);
    }

    @Override
    public void read() {
        var startIdx = contentStartIdx();

        var nextIdx = scanSetVariableToken(startIdx);
        nextIdx = scanAsToken(nextIdx);
        nextIdx = scanOnToken(nextIdx);
        scanSortOrderToken(nextIdx);
    }

    private int scanSetVariableToken(int startIdx) {
        Optional<Token> optTargetToken = ExpressibleValueScanner.findNext(startIdx, content());
        if (optTargetToken.isEmpty()) {
            throw new InvalidSyntaxException("No token found", startIdx);
        }

        setVarToken = optTargetToken.get();
        setVarToken.read();

        return Reader.nextValidCharIndex(setVarToken.endIdx() + 1, content());
    }

    private int scanAsToken(int startIdx) {
        var optAsToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optAsToken.isEmpty() || !optAsToken.get().type().equals(TokenType.AS)) {
            throw new InvalidSyntaxException("AS token expected", startIdx);
        }

        asToken = optAsToken.get();
        asToken.read();

        return Reader.nextValidCharIndex(asToken.endIdx() + 1, content());
    }

    private int scanOnToken(int startIdx) {
        var optOnToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optOnToken.isEmpty() || !optOnToken.get().type().equals(TokenType.ON)) {
            throw new InvalidSyntaxException("ON token expected", startIdx);
        }

        onToken = optOnToken.get();
        onToken.read();

        return Reader.nextValidCharIndex(onToken.endIdx() + 1, content());
    }

    private void scanSortOrderToken(int startIdx) {
        var optSortOrderToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optSortOrderToken.isEmpty() || !optSortOrderToken.get().type().equals(TokenType.SORT_ORDER)) {
            throw new InvalidSyntaxException("Ordering token (ASC, DESC) expected", startIdx);
        }

        sortOrderToken = optSortOrderToken.get();
        sortOrderToken.read();

        endIdx = sortOrderToken.endIdx();
    }

    @Override
    public Object evaluate(ContextHolder context) {
        return null;
    }
}
