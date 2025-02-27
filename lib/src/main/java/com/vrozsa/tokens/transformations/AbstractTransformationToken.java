package com.vrozsa.tokens.transformations;

import com.vrozsa.Reader;
import com.vrozsa.exceptions.InvalidSyntaxException;
import com.vrozsa.scanners.AuxiliaryTokenScanner;
import com.vrozsa.scanners.ExpressibleValueScanner;
import com.vrozsa.tokens.AbstractToken;
import com.vrozsa.tokens.Token;
import com.vrozsa.tokens.TokenInput;
import com.vrozsa.tokens.TokenType;

import java.util.Optional;

abstract class AbstractTransformationToken extends AbstractToken {
    private final boolean asTokenMandatory;
    protected Token setVarToken;
    protected AsToken asToken;
    protected OnToken onToken;

    AbstractTransformationToken(TokenType type, TokenInput input,boolean asTokenMandatory) {
        super(type, input);
        this.asTokenMandatory = asTokenMandatory;
    }

    @Override
    public void read() {
        var startIdx = contentStartIdx();

        var nextIdx = scanSetVariableToken(startIdx);

        var idxAfterAs = scanAsToken(nextIdx);
        if (idxAfterAs > nextIdx) {
            // If AS is present, ON is required
            scanOnToken(idxAfterAs);
        }
        else if (asTokenMandatory) {
            throw new InvalidSyntaxException("AS token expected", startIdx);
        }
    }

    private int scanSetVariableToken(int startIdx) {
        Optional<Token> optTargetToken = ExpressibleValueScanner.findNext(startIdx, content());
        if (optTargetToken.isEmpty()) {
            throw new InvalidSyntaxException("No token found", startIdx);
        }

        setVarToken = optTargetToken.get();
        setVarToken.read();

        endIdx = setVarToken.endIdx();

        return Reader.nextValidCharIndex(setVarToken.endIdx() + 1, content());
    }

    private int scanAsToken(int startIdx) {
        var optAsToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optAsToken.isEmpty() || !optAsToken.get().type().equals(TokenType.AS)) {
            return startIdx;
        }

        asToken = (AsToken) optAsToken.get();
        asToken.read();

        endIdx = asToken.endIdx();

        return Reader.nextValidCharIndex(asToken.endIdx() + 1, content());
    }

    private void scanOnToken(int startIdx) {
        var optOnToken = AuxiliaryTokenScanner.instance().findNext(startIdx, content());
        if (optOnToken.isEmpty() || !optOnToken.get().type().equals(TokenType.ON)) {
            throw new InvalidSyntaxException("ON token expected", startIdx);
        }

        onToken = (OnToken) optOnToken.get();
        onToken.read();

        endIdx = onToken.endIdx();
    }
}
