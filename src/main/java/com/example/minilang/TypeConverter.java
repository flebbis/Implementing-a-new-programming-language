package com.example.minilang;

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
}
