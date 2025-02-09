package com.vrozsa;

public class Main {
    public static void main(String[] args) {


        var content = new FileReader().readFile("sample_template.yml").toCharArray();

        var startIdx = 0;
        var result = ExpressionScanner.scan(startIdx, content);
        System.out.println("Scanned content: " + result);
    }
}
