package com.example.minilang.codegen;

public class PrintGlobalGenerator {
    private static int global = 0;

    public static String generatePrintGlobal(){

        return ("@.str" + global++);
    }
}
