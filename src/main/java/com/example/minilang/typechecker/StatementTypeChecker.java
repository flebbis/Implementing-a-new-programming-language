package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

public class StatementTypeChecker {

    private Context context;
    private ExpressionTypeChecker expressionTypeChecker;

    public StatementTypeChecker(Context context) {
        this.context = context;
        this.expressionTypeChecker = new ExpressionTypeChecker(context);
    }

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


    public Ast.SInit typeCheck(Ast.SInit sInit) {
        if(sInit.type() == Ast.Type.TUnknown) {
            // TODO: OBSERVE THIS HELLOOO LANGUAGE SERVER
            Ast.Exp exp = expressionTypeChecker.typeCheck(sInit.value());
            context.pushToCurrentScope(sInit.name(), exp.type());
            return new Ast.SInit(exp.type(), sInit.name(), exp, sInit.pos());
        } else {
            Ast.Exp exp = expressionTypeChecker.typeCheck(sInit.value());
            if(sInit.type() != exp.type()) {

                // Allow implicit conversion from int to double
                if(sInit.type() == Ast.Type.TDouble && exp.type() == Ast.Type.TInt) {
                    exp = new Ast.EDInt(exp, exp.type(), exp.pos());

                } else {
                    throw new TypeException("Incorrect initialisation of type " + exp.type(), exp.pos());
                }
            }
            context.pushToCurrentScope(sInit.name(), sInit.type());
            return new Ast.SInit(sInit.type(), sInit.name(), exp, sInit.pos());
        }
    }

    public Ast.SExp typeCheck(Ast.SExp stmt) {
        Ast.Exp exp = expressionTypeChecker.typeCheck(stmt.exp());
        return new Ast.SExp(exp, stmt.pos());
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
