package com.example.minilang.codegen;

import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;

public class Helper {

    public String convertType(Ast.Type type) {
        return switch (type) {
            case Ast.TInt() -> "i32";
            case Ast.TBool() -> "i1";
            case Ast.TArray(var elementType, var arraySize) -> getArrayStructType(type) + "*";
            case Ast.TString() -> "i8*";
            case Ast.TDouble() -> "double";
            case Ast.TUnresolved(String id, java.util.List<Ast.Type> conditions) -> getUnresolvedType((Ast.TUnresolved) type);
            case Ast.TUnknown() -> "void"; // Default to pointer type for unknown types. UGLY! FIX LATER!
            default -> throw new IllegalArgumentException("Unsupported type: " + TypeConverter.typeToString(type));
        };
    }

    // Void and arrays are not supported for this
    public String convertScalarType(Ast.Type type) {
        return switch (type) {
            case Ast.TInt() -> "i32";
            case Ast.TBool() -> "i1";
            case Ast.TString() -> "i8*";
            case Ast.TDouble() -> "double";
            case Ast.TUnresolved(String id, java.util.List<Ast.Type> conditions) -> getUnresolvedType((Ast.TUnresolved) type);
            default -> throw new IllegalArgumentException("Unsupported type: " + TypeConverter.typeToString(type));
        };
    }

    private String getUnresolvedType(Ast.TUnresolved unresolved) {
        if(unresolved.conditions().isEmpty()) {
            return "i32"; // default for now
        } else {
            return convertType(unresolved.conditions().getFirst()); // return first
        }
    }

    public String getArrayDataPointerType(Ast.Type arrayType) {
        if (!(arrayType instanceof Ast.TArray arr)) {
            throw new IllegalArgumentException("Expected array type, got: " + TypeConverter.typeToString(arrayType));
        }

        return switch (arr.elementType()) {
            case Ast.TInt() -> "i32*";
            case Ast.TBool() -> "i1*";
            case Ast.TDouble() -> "double*";
            case Ast.TString() -> "i8**";
            default -> throw new IllegalArgumentException(
                    "Unsupported array element type: " + TypeConverter.typeToString(arr.elementType()));
        };
    }

    public String getArrayStructType(Ast.Type arrayType) {
        if (!(arrayType instanceof Ast.TArray arr)) {
            throw new IllegalArgumentException("Expected array type, got: " + TypeConverter.typeToString(arrayType));
        }

        return switch (arr.elementType()) {
            case Ast.TInt() -> "%array_i32";
            case Ast.TBool() -> "%array_i1";
            case Ast.TDouble() -> "%array_double";
            case Ast.TString() -> "%array_i8ptr";
            default -> throw new IllegalArgumentException(
                    "Unsupported array element type: " + TypeConverter.typeToString(arr.elementType()));
        };
    }

    public String getArrayElementType(Ast.Type arrayType) {
        if (!(arrayType instanceof Ast.TArray arr)) {
            throw new IllegalArgumentException("Expected array type, got: " + TypeConverter.typeToString(arrayType));
        }

        return convertScalarType(arr.elementType());
    }

    public int getTypeSizeBytes(Ast.Type type) {
        return switch (type) {
            case Ast.TInt() -> 4;
            case Ast.TDouble() -> 8;
            case Ast.TBool() -> 1;
            case Ast.TString() -> 8; // pointer size on 64-bit
            case Ast.TArray(var elementType, int size) -> getTypeSizeBytes(elementType);
            default -> throw new IllegalArgumentException("Unsupported type size for: " + TypeConverter.typeToString(type));
        };
    }

}
