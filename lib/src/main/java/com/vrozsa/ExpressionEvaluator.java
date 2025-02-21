package com.vrozsa;

import com.vrozsa.tokens.Expression;
import com.vrozsa.tokens.Token;

import java.util.List;

class ExpressionEvaluator {
    private final ContextHolder context;
    private final List<Token> expressions;
    private final String contentAsText;

    ExpressionEvaluator(ContextHolder context, List<Token> expressions, char[] content) {
        this(context, expressions, String.copyValueOf(content));
    }

    ExpressionEvaluator(ContextHolder context, List<Token> expressions, String contentAsText) {
        this.context = context;
        this.expressions = expressions;
        this.contentAsText = contentAsText;
    }

    /**
     * Build the new content with the result of the evaluated expressions.
     * @return the evaluated content.
     */
    public String evaluate() {
        evaluateExpressions();

        return buildEvaluatedContent();
    }

    private void evaluateExpressions() {
        expressions.forEach(e -> e.evaluate(context));
    }

    private String buildEvaluatedContent() {

        var newContent = new StringBuilder();

        var contentIdx = 0;

        for (var currExp : expressions) {
            // -1 for the $
            var startIdx = expressionStartIdx(currExp);

            var segment = contentAsText.substring(contentIdx, startIdx);
            newContent.append(segment);

            var expValue = currExp.result();
            newContent.append(expValue);
            // +1 for the $ and +1 to get to the next char after the expression
            contentIdx += segment.length() + tokenLength(currExp);
        }

        // Add the rest of the content
        var lastExp = expressions.get(expressions.size() - 1);

        var lastSegment = contentAsText.substring(expressionEndIdx(lastExp));
        newContent.append(lastSegment);

        return newContent.toString();
    }

    private static int expressionStartIdx(Token token) {
        // -1 for the $
        var startIdx = token.startIdx();
        if (token instanceof Expression) {
            startIdx--;
        }
        return startIdx;
    }

    private static int expressionEndIdx(Token token) {
        var endIdx = token.endIdx();
        if (token instanceof Expression) {
            endIdx++;
        }

        return endIdx;
    }

    private static int tokenLength(Token token) {
        int length = token.endIdx() - token.startIdx();
        if (token instanceof Expression) {
            length += 2;
        }

        return length;
    }
}
