package com.example.minilang;

public class LabelGenerator {
    private int labelCount = 0;
    
    public String generateLabel(String prefix){
        return prefix + "_" + labelCount++;
    }
}