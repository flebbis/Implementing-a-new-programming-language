package com.example.minilang.codegen;

import com.example.minilang.ast.Ast;

public class Helper {

    public String convertType(Ast.Type type) {
        return switch (type) {
            case Ast.TInt() -> "i32";
            case Ast.TBool() -> "i1";
            case Ast.TArray(var elementType) -> "temp_array_type"; // Placeholder, you will need to handle array types more robustly.
            case Ast.TString() -> "i8*";
            case Ast.TDouble() -> "double";
            case Ast.TUnknown() -> "i32"; // Default to pointer type for unknown types. UGLY! FIX LATER!

        };

    
    }
}
