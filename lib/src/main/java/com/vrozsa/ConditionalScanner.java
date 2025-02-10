package com.vrozsa;

import com.vrozsa.scanners.ExpressionScanner;

public class ConditionalScanner {

    // Can be var, expression, var operator var
    public static Conditional scan(int startIdx, char[] content) {

        if (ExpressionScanner.isNextTokenAnExpression(startIdx, content)) {
            return new Conditional(startIdx, content);
        }

    return null;

    }

    public static class Conditional extends Expression {
        public Conditional(int startIdx, char[] content) {
            super(startIdx, content);
        }
    }
}
