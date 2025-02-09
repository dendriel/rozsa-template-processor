package com.vrozsa;

import java.util.List;

public class ExpressionScanner {

    private static final Character EXPRESSION_TOKEN = '$';
    private static final Character ESCAPE_TOKEN = '\\';
    private static final List<Character> SEPARATOR_TOKENS = List.of(' ', '\t');

    public static Expression[] scan(final int idx, final char[] text) {

        for (int i = idx; i < text.length; i++) {

            var nextChar = text[i];

            if (ESCAPE_TOKEN.equals(nextChar)) {
                // If the next char is the escape character, skip the next char.
                i++;
                System.out.println("Skipped escaped token \\" + text[i] + " at " + (i - 1));
                continue;
            }

            if (!EXPRESSION_TOKEN.equals(nextChar)) {
                continue;
            }

            var expressionStartIdx = i + 1;
            Expression expression = new Expression(expressionStartIdx, text);

            expression.read();

//            int endIndx = expression.endIndex();

            System.out.println("Expression starts at " + i);
        }


        return new Expression[0];
    }

    public static boolean isNextTokenAnExpression(final int idx, final char[] text) {
        for (int i = idx; i < text.length; i++) {

            var nextChar = text[i];

            if (SEPARATOR_TOKENS.contains(nextChar)) {
                // skip blank characters
                continue;
            }

            if (ESCAPE_TOKEN.equals(nextChar)) {
                // If the next char is the escape character, the next token is not an expression.
                System.out.println("Found escaped token \\" + text[i] + " at " + (i - 1));
                return false;
            }

            return EXPRESSION_TOKEN.equals(nextChar);
        }

        return false;
    }
}
