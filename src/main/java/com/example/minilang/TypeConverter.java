package com.example.minilang;

import com.example.minilang.ast.Ast;

//Singleton pattern for type conversion
public class TypeConverter {

    public static Ast.Type mapType(String text) {
        return switch (text) {
            case "int" -> new Ast.TInt();
            case "double" -> new Ast.TDouble();
            case "string" -> new Ast.TString();
            case "bool" -> new Ast.TBool();
            default -> new Ast.TUnknown();
        };
    }

    public static String typeToString(Ast.Type type) {
        if (type instanceof Ast.TInt) return "int";
        if (type instanceof Ast.TDouble) return "double";
        if (type instanceof Ast.TString) return "string";
        if (type instanceof Ast.TBool) return "bool";
        if (type instanceof Ast.TArray arr) return "[" + typeToString(arr.elementType()) + "]";
        return "unknown";
    }
}
