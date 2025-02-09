package com.vrozsa;

public class Main {
    public static void main(String[] args) {




        var expressionScanner = new ExpressionScanner();

        var content = new FileReader().readFile("sample_template.yml").toCharArray();

        var result = expressionScanner.scan(content);
        System.out.println("Scanned content: " + result);
    }
}
