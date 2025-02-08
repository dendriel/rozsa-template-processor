package com.vrozsa;

public class Main {
    public static void main(String[] args) {




        var expressionScanner = new ExpressionScanner();

        String content = new FileReader().readFile("sample_template.yml");

        var result = expressionScanner.scan(content);
        System.out.println("Scanned content: " + result);
    }
}
