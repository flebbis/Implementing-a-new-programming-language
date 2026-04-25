package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

/**
 * Small utility for comparing Ast.Type values.
 * Specifically treats array types as equal when their element types are equal,
 * ignoring arraySize. Comparison is recursive for nested arrays.
 */
public class TypeUtils {

    public static boolean equalTypes(Ast.Type a, Ast.Type b) {
        if (a == null || b == null) return false;
        if (a instanceof Ast.TArray && b instanceof Ast.TArray) {
            Ast.TArray aa = (Ast.TArray) a;
            Ast.TArray bb = (Ast.TArray) b;
            // Compare element types recursively and ignore arraySize
            return equalTypes(aa.elementType(), bb.elementType());
        }

        if (a instanceof Ast.TUnresolved) {
            return unresolvedCheck((Ast.TUnresolved) a, b);
        } else if(b instanceof Ast.TUnresolved) {
            return unresolvedCheck((Ast.TUnresolved) b, a);
        }

        // Default: use record equality for simple types (int, double, string, bool, unknown)
        return a.equals(b);
    }

    private static boolean unresolvedCheck(Ast.TUnresolved a, Ast.Type b) {
        if(b instanceof Ast.TUnresolved bUnresolved) {
            if(a.conditions().isEmpty() && bUnresolved.conditions().isEmpty()) {
                return true; // Both are unresolved with no conditions, consider them equal
            }
        }

        if(a.conditions().isEmpty()) {
            return false; // Unresolved with no conditions can match any type
        }

        for(Ast.Type condition : a.conditions()) {
            if(TypeUtils.equalTypes(condition, b)) {
                return true; // If any condition matches, consider it equal
            }
        }
        return false; // No conditions matched
    }

    public static boolean arrayIsUnkown(Ast.TArray type) {
        if(type.elementType() instanceof Ast.TUnknown) {
            return true;
        }
        if(type.elementType() instanceof Ast.TArray) {
            Ast.TArray tArray = (Ast.TArray) type.elementType();
            return arrayIsUnkown(tArray);
        } 
        return false;
    }

    public static boolean isNumeric(Ast.Type type) {
        return type instanceof Ast.TInt || type instanceof Ast.TDouble;
    }
}
