package com.vrozsa;

public class Main {
    public static void main(String[] args) {


        var content = new FileReader().readFile("sample_template_0.yml").toCharArray();

        var context = ContextHolder.create()
                .add("valid_user", false)
                .add("user.type", "GUEST")
                .add("invalid_user", true)
                .add("user.type2", "ADMIN");

        var expressions = ExpressionScanner.scan(0, content);
        System.out.println("Scanned content: " + expressions);


        // this logic may go in the ExpressionEvaluator
        expressions.forEach(e -> e.evaluate(context));

        var newContent = new StringBuilder();
        var contentAsText = String.copyValueOf(content);

        var contentIdx = 0;

        for (var currExp : expressions) {
            var startIdx = currExp.startIdx() - 1; // -1 for the $

            var segment = contentAsText.substring(contentIdx, startIdx);
            newContent.append(segment);

            var expValue = currExp.getResult();
            newContent.append(expValue);
            contentIdx += segment.length() + currExp.length() + 2; // next char after expression
        }

        // Add the rest of the content
        var lastExp = expressions.get(expressions.size() - 1);

        var lastSegment = contentAsText.substring(lastExp.endIdx() + 1);
        newContent.append(lastSegment);

        System.out.println("\n\n\n\nEvaluated Content:\n" + newContent);

    }

}
