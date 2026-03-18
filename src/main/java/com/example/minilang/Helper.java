package com.example.minilang;

public class Helper {

    public String convertType(Ast.Type type) {
        return switch (type) {
            case TInt -> "i32";
            case TBool -> "i1";
            case TVoid -> "void";
            case TString -> "i8*";
            case TDouble -> "double";
            case TUnknown -> "i32"; // Default to pointer type for unknown types. UGLY! FIX LATER!

        };

    
    }

    // public String convertType(Ast.Exp type){

    //     if (type instanceof Ast.EInt ) {return "i32";}
    //     return "test";
    // }





}