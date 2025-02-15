package com.vrozsa;

import com.vrozsa.tokens.Expression;

import java.util.List;

class ExpressionEvaluator {
    private final ContextHolder context;
    private final List<Expression> expressions;
    private final String contentAsText;

    ExpressionEvaluator(ContextHolder context, List<Expression> expressions, char[] content) {
        this(context, expressions, String.copyValueOf(content));
    }

    ExpressionEvaluator(ContextHolder context, List<Expression> expressions, String contentAsText) {
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
            var startIdx = currExp.startIdx() - 1;

            var segment = contentAsText.substring(contentIdx, startIdx);
            newContent.append(segment);

            var expValue = currExp.result();
            newContent.append(expValue);
            // +1 for the $ and +1 to get to the next char after the expression
            contentIdx += segment.length() + currExp.length() + 2;
        }

        // Add the rest of the content
        var lastExp = expressions.get(expressions.size() - 1);

        var lastSegment = contentAsText.substring(lastExp.endIdx() + 1);
        newContent.append(lastSegment);

        return newContent.toString();
    }
}
