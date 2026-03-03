package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

public class ExpressionTypeChecker {
    public Ast.Exp typeCheck(Ast.Exp exp) {
        System.out.println("Type checking expression: " + exp.getClass().getSimpleName());
        return switch(exp) {
            case Ast.EAnd eAnd -> typeCheck(eAnd);
            case Ast.EOr eOr -> typeCheck(eOr);
            case Ast.EEq eEq -> typeCheck(eEq);
            case Ast.ELt eLt -> typeCheck(eLt);
            case Ast.EGe eGe -> typeCheck(eGe);
            case Ast.ELe eLe -> typeCheck(eLe);
            case Ast.EOpp eOpp -> typeCheck(eOpp);
            case Ast.EInt eInt -> typeCheck(eInt);
            case Ast.EId eId -> typeCheck(eId);
            case Ast.EBool eBool -> typeCheck(eBool);
            case Ast.EString eString -> typeCheck(eString);
            case Ast.EDec eDec -> typeCheck(eDec);
            case Ast.EInc eInc -> typeCheck(eInc);
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

    public Ast.Exp typeCheck(Ast.EDec eDec) {
        if(eDec.exp().type() == Ast.Type.TInt) {
            return eDec;
        } else {
            throw new TypeException("Cannot decrement " + eDec.exp().type(), eDec.pos());
        }
    }
}
