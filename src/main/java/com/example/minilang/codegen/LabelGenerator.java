package com.example.minilang.codegen;

public class LabelGenerator {
    private static int labelCount;

    public LabelGenerator() {
        labelCount = 0;
    }

    public static String generateLabel(String prefix) {
        return prefix + "_" + (labelCount++);
    }
}
