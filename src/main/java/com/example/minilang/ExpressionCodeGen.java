package com.example.minilang;

public class ExpressionCodeGen {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.

    public ExpressionCodeGen() {
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generateExpression(Ast.Exp exp) {
        // Here you would implement the logic to generate LLVM IR code for an expression.
        // This would involve:
        // 1. Determining the type of expression (e.g., binary operation, function call, etc.)
        // 2. Generating the appropriate LLVM IR code based on the type of expression and its contents.
    }
}