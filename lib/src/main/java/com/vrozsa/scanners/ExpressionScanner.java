package com.vrozsa.scanners;

import com.vrozsa.CharacterChecker;
import com.vrozsa.CharacterRange;
import com.vrozsa.CharacterSingle;
import com.vrozsa.EscapeCharacter;
import com.vrozsa.tokens.Expression;
import com.vrozsa.Reader;
import com.vrozsa.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Scans for expressions in the content.
 */
public class ExpressionScanner {

    private static final CharacterChecker expressionCharChecker = CharacterChecker.of(
            new CharacterSingle('$')
    );

    private static final CharacterChecker escapeCharValueChecker = CharacterChecker.of(
            new CharacterSingle('$'),
            new CharacterSingle('.'),
            new CharacterSingle('_'),
            new CharacterSingle('-')
    );

    private static final CharacterChecker escapeCharChecker = CharacterChecker.of(
            new CharacterSingle('\\')
    );

    /**
     * Scans for expressions in the content.
     * @param idx the start of the content to scan for expressions
     * @param content the content to be scanned.
     * @return a list of expressions found in the content.
     */
    public static List<Token> scan(final int idx, final char[] content) {
        var expressions = new ArrayList<Token>();

        int startIdx = Reader.nextValidCharIndex(idx, content);

        for (int i = startIdx; i < content.length; i++) {

            var nextChar = content[i];

            if (escapeCharChecker.match(nextChar)) {
                // move to the next char

                if (i >= content.length) {
                    // end of the template.
                    continue;
                }


                var escapeCharValue = content[i + 1];
                if (!escapeCharValueChecker.match(escapeCharValue)) {
                    continue;
                }

                // Store the escape char '\' to be removed in the final template.
                expressions.add(new EscapeCharacter(i));

                i++;

                // If the next char is the escape character, skip the next char.
                System.out.println("Skipped escaped token \\" + content[i] + " at " + i);
            }

            if (!expressionCharChecker.match(nextChar)) {
                continue;
            }

            var expressionStartIdx = i + 1;
            var expression = new Expression(expressionStartIdx, content);

            expression.read();
            expressions.add(expression);

            // Skip the whole expression body.
            i += expression.length() + 1;
        }

        return expressions;
    }

    public static boolean isNextTokenAnExpression(final int idx, final char[] content) {
        var nextChar = content[idx];
        return expressionCharChecker.match(nextChar);
    }
}
