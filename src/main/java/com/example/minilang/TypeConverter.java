package com.example.minilang;

import com.example.minilang.ast.Ast;

//Singleton pattern for type conversion
public class TypeConverter {

    public static Ast.Type mapType(String text) {
        return switch (text) {
            case "int" -> Ast.Type.TInt;
            case "double" -> Ast.Type.TDouble;
            case "string" -> Ast.Type.TString;
            case "bool" -> Ast.Type.TBool;
            default -> Ast.Type.TUnknown;
        };
    }

    public static String typeToString(Ast.Type type) {
        return switch (type) {
            case TInt -> "int";
            case TDouble -> "double";
            case TString -> "string";
            case TBool -> "bool";
            default -> "unknown";
        };
    }
}
