package com.vrozsa;

import com.vrozsa.scanners.ExpressionScanner;

public class TemplateProcessor {

    public String process(String content, ContextHolder context) {
        return process(content.toCharArray(), context);
    }

    public String process(char[] content, ContextHolder context) {
        var expressions = ExpressionScanner.scan(0, content);

        var contentEvaluator = new ExpressionEvaluator(context, expressions, content);
        return contentEvaluator.evaluate();
    }
}
