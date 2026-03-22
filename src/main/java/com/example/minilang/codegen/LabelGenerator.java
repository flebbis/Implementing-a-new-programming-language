package com.example.minilang.codegen;

public class LabelGenerator {
    private int labelCount;

    public LabelGenerator() {
        this.labelCount = 0;
    }

    public String generateLabel(String prefix) {
        return prefix + "_" + (labelCount++);
    }
}
