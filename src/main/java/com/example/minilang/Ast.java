package com.example.minilang; // AST Definition

/**
 * One file to rule them all.
 * This defines the entire structure of your Abstract Syntax Tree.
 */
public class Ast {

    // The base type for all nodes
    // 'sealed' = only the specific records listed below can implement this.
    public sealed interface Exp permits EInt, EOpp {}

    // A leaf node representing a number (e.g., 3)
    public record EInt(int value) implements Exp {}

    // A node representing an operation (e.g., 3 + 5)
    public record EOpp(Exp left, Exp right, Op op) implements Exp {}

    // The operators we support
    public enum Op {
        ADD, SUB, MUL, DIV
    }
}