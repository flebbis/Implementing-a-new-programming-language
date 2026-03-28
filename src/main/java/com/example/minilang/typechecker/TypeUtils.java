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
        // Default: use record equality for simple types (int, double, string, bool, unknown)
        return a.equals(b);
    }
}
