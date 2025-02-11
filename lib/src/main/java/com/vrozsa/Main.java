package com.vrozsa;

import com.vrozsa.scanners.ExpressionScanner;

public class Main {
    public static void main(String[] args) {
        var content = new FileReader().readFile("sample_template_1.yml").toCharArray();

        var context = ContextHolder.create()
                .add("valid_user", false)
                .add("user.type", "GUEST")
                .add("invalid_user", true)
                .add("xpto_value", 123)
                .add("foo", 123)
                .add("bar", "xxx")
                .add("equality_result", "values are equal")
                .add("inequality_result", "this value wont show")
                .add("fallback_result", "fallback result from else")
                .add("result_then", "result when then value is used")
                .add("user.type2", "ADMIN");

        var expressions = ExpressionScanner.scan(0, content);

        var contentEvaluator = new ExpressionEvaluator(context, expressions, content);
        var evaluatedContent = contentEvaluator.evaluate();


        System.out.println("\n\n\n\nEvaluated Content:\n" + evaluatedContent);

    }

}
