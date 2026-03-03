package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

public class StatementTypeChecker {
    public Ast.Stmt typeCheck(Ast.Stmt stmt) {
        return switch (stmt) {
            case Ast.SWhile SWhile -> typeCheck(SWhile);
            case Ast.SDo SDo -> typeCheck(SDo);
            case Ast.SReturn SRet -> typeCheck(SRet);
            case Ast.SIf SifStmt -> typeCheck(SifStmt);
            case Ast.SBlock SBlock -> typeCheck(SBlock);
            case Ast.SDecl SDecl -> typeCheck(SDecl);
            case Ast.SInit SInit -> typeCheck(SInit);
            case Ast.SExp SExp -> typeCheck(SExp);
        };
    }

    public Ast.Stmt typeCheck(Ast.SWhile stmt) {
        // Type check the condition and body of the while loop
        // Ensure the condition is of type bool
        // Return an annotated SWhile statement
        return stmt; // Placeholder
    }

    public Ast.Stmt typeCheck(Ast.SDo stmt) {
        // Type check the condition and body of the do loop
        // Ensure the condition is of type bool
        // Return an annotated SDo statement
        return stmt; // Placeholder
    }

    public Ast.Stmt typeCheck(Ast.SReturn stmt) {
        // Type check the return expression
        // Ensure it matches the expected return type of the function
        // Return an annotated SReturn statement
        return stmt; // Placeholder
    }

    public Ast.Stmt typeCheck(Ast.SIf stmt) {
        // Type check the condition and both branches of the if statement
        // Ensure the condition is of type bool
        // Return an annotated SIf statement
        return stmt; // Placeholder
    }
}
