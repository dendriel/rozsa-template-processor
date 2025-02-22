package com.vrozsa;

import com.vrozsa.scanners.ExpressionScanner;

public class Main {
    public static void main(String[] args) {
        var content = FileReader.readFile("sample_template_2.yml").toCharArray();

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
                .add("user.type2", "ADMIN")
                .add("value01", 1234);

        var evaluatedContent = new TemplateProcessor().process(content, context);

        System.out.println("\nEvaluated Content:\n\n" + evaluatedContent);

    }
}
