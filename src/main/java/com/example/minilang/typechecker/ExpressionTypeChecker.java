package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

public class ExpressionTypeChecker {
    public Ast.Exp typeCheck(Ast.Exp exp) {
        System.out.println("Type checking expression: " + exp.getClass().getSimpleName());
        return switch(exp) {
            case Ast.ECmp cmp -> typeCheck(cmp);
            case Ast.ENot not -> typeCheck(not);
            case Ast.ELogic eLogic -> typeCheck(eLogic);
            case Ast.EOpp eOpp -> typeCheck(eOpp);
            case Ast.EInt eInt -> typeCheck(eInt);
            case Ast.EId eId -> typeCheck(eId);
            case Ast.EBool eBool -> typeCheck(eBool);
            case Ast.EString eString -> typeCheck(eString);

            case Ast.ECall eCall -> typeCheck(eCall);
            // More to be implemented
            default -> throw new TypeException("Unknown expression type: " + exp.getClass().getSimpleName(), exp.pos());
        };
    }

    public Ast.Exp typeCheck(Ast.EInt eInt) {
        return eInt;
    }

    public Ast.Exp typeCheck(Ast.EBool eBool) {
        return eBool;
    }

    public Ast.Exp typeCheck(Ast.EString eString) {
        return eString;
    }

    public Ast.Exp typeCheck(Ast.ECmp eCmp) {
        Ast.Type leftType = typeCheck(eCmp.left()).type();
        Ast.Type rightType = typeCheck(eCmp.right()).type();
        // TODO: Handle string comparisons if needed, handle double to int comparisons
        if(leftType == rightType && (leftType == Ast.Type.TInt || leftType == Ast.Type.TDouble)) {
            return eCmp; // Return the original expression with the same type
        } else {
            throw new TypeException("Comparison operator requires both operands to be of the same type (int or double)", eCmp.pos());
        }
    }

    // TODO: create context, verify ID is declared
    public Ast.Exp typeCheck(Ast.EId eId) {
        return null; // Placeholder
    }

}
