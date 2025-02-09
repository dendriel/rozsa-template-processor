package com.vrozsa;

import java.util.ArrayList;
import java.util.List;

public class ExpressionScanner {

    private static final Character EXPRESSION_TOKEN = '$';
    private static final Character ESCAPE_TOKEN = '\\';

    public static List<Expression> scan(final int idx, final char[] content) {

        var expressions = new ArrayList<Expression>();

        int startIdx = Reader.nextValidCharIndex(idx, content);

        for (int i = startIdx; i < content.length; i++) {

            var nextChar = content[i];

            if (ESCAPE_TOKEN.equals(nextChar)) {
                // If the next char is the escape character, skip the next char.
                i++;
                System.out.println("Skipped escaped token \\" + content[i] + " at " + (i - 1));
                continue;
            }

            if (!EXPRESSION_TOKEN.equals(nextChar)) {
                continue;
            }

            var expressionStartIdx = i + 1;
            var expression = new Expression(expressionStartIdx, content);

            expression.read();

            // find closing bracket
//            int endIndx = expression.endIndex();

            expressions.add(expression);

            System.out.println(String.format("Expression starts at %d, %d and ends at %d", expressionStartIdx, expression.startIdx(), expression.endIdx()));
        }


        return expressions;
    }

    public static boolean isNextTokenAnExpression(final int idx, final char[] content) {
        var nextChar = content[idx];

        if (ESCAPE_TOKEN.equals(nextChar)) {
            // If the next char is the escape character, the next token is not an expression.
            System.out.println("Found escaped token \\" + content[idx] + " at " + (idx - 1));
            return false;
        }

        return EXPRESSION_TOKEN.equals(nextChar);
    }
}
