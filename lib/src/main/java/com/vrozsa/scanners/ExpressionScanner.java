package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterSingle;
import com.vrozsa.Expression;
import com.vrozsa.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Scans for expressions in the content.
 */
public class ExpressionScanner {

    private static final CharacterChecker expressionCharChecker = CharacterChecker.of(
            new CharacterSingle('$')
    );
    private static final Character ESCAPE_TOKEN = '\\';

    private static final CharacterChecker escapeCharChecker = CharacterChecker.of(
            new CharacterSingle('\\')
    );

    /**
     * Scans for expressions in the content.
     * @param idx the start of the content to scan for expressions
     * @param content the content to be scanned.
     * @return a list of expressions found in the content.
     */
    public static List<Expression> scan(final int idx, final char[] content) {
        var expressions = new ArrayList<Expression>();

        int startIdx = Reader.nextValidCharIndex(idx, content);

        for (int i = startIdx; i < content.length; i++) {

            var nextChar = content[i];

            if (escapeCharChecker.match(nextChar)) {
                // If the next char is the escape character, skip the next char.
                i++;
                System.out.println("Skipped escaped token \\" + content[i] + " at " + (i - 1));
                continue;
            }

            if (!expressionCharChecker.match(nextChar)) {
                continue;
            }

            var expressionStartIdx = i + 1;
            var expression = new Expression(expressionStartIdx, content);

            expression.read();
            expressions.add(expression);

            // Skip the whole expression body.
            i += expression.endIdx();
        }

        return expressions;
    }

    public static boolean isNextTokenAnExpression(final int idx, final char[] content) {
        var nextChar = content[idx];
        return expressionCharChecker.match(nextChar);
    }
}
