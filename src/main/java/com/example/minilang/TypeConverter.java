package com.example.minilang;

import com.example.minilang.ast.Ast;

//Singleton pattern for type conversion
public class TypeConverter {

    public static Ast.Type mapType(String text) {
        // Array case
        if (text.startsWith("[") && text.endsWith("]")) {
            String inner = text.substring(1, text.length() - 1);
            Ast.Type innerType = mapType(inner); // recursion for innter type
            return new Ast.TArray(innerType, 0);
        }

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
        if (type instanceof Ast.TArray arr) {
            if (arr.elementType() instanceof Ast.TUnknown) {
                return "unkown";
            }
            return "[" + typeToString(arr.elementType()) + "]";
        }
        return "unknown";
    }
}
