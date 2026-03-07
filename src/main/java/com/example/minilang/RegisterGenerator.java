package com.example.minilang;

public class RegisterGenerator {
    private int regCounter;

    public RegisterGenerator() {
        this.regCounter = 0;
    }

    public String nextReg() {
        return String.valueOf(++regCounter);
    }

    public void reset() {
        this.regCounter = 0;
    }
}