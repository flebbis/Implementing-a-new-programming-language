package com.example.minilang.codegen;

public class RegisterGenerator{
    private static int reg = 0;

    public static String generateRegister(){

        return ("%" + reg++);
    }

}
