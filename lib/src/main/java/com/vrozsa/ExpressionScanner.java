package com.vrozsa;

class ExpressionScanner {

    private static final Character EXPRESSION_TOKEN = '$';

    Expression[] scan(final char[] text) {

        for (int i = 0; i < text.length; i++) {

            var nextChar = text[i];

            if (!EXPRESSION_TOKEN.equals(nextChar)) {
                continue;
            }

            // TODO: check if inst escaped.

            var expressionStartIdx = i + 1;
            Expression expression = new Expression(expressionStartIdx, text);

            expression.evaluate();

//            int endIndx = expression.endIndex();

            System.out.println("Expression starts at " + i);
        }


        return new Expression[0];
    }
}
