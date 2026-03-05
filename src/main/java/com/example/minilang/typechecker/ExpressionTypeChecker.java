package com.example.minilang.typechecker;

import com.example.minilang.Pos;
import com.example.minilang.ast.Ast;

public class ExpressionTypeChecker {

    private Context context = new Context();

    public ExpressionTypeChecker(Context context) {
        this.context = context;
    }

    public Ast.Exp typeCheck(Ast.Exp exp) {
        System.out.println("Type checking expression: " + exp.getClass().getSimpleName());
        return switch(exp) {
            case Ast.EInt eInt -> typeCheck(eInt);
            case Ast.EDouble eDouble -> typeCheck(eDouble);
            case Ast.EString eString -> typeCheck(eString);
            case Ast.EBool eBool -> typeCheck(eBool);
            case Ast.EId eId -> typeCheck(eId);
            case Ast.ECall eCall -> typeCheck(eCall);
            case Ast.ENot eNot -> typeCheck(eNot);
            case Ast.EPower ePower -> typeCheck(ePower);
            case Ast.EOpp eOpp -> typeCheck(eOpp);
            case Ast.ECmp cmp -> typeCheck(cmp);
            case Ast.ELogic eLogic -> typeCheck(eLogic);
            case Ast.EAss eAss -> typeCheck(eAss);
            case Ast.EArrayIndex eArrayIndex -> typeCheck(eArrayIndex);
            case Ast.EUnary eUnary -> typeCheck(eUnary);


            // More to be implemented
            default -> throw new TypeException("Unknown expression type: " + exp.getClass().getSimpleName(), exp.pos());
        };
    }

    public Ast.Exp typeCheck(Ast.EInt eInt) {return eInt;}

    public Ast.Exp typeCheck(Ast.EDouble eDouble) {return eDouble;}

    public Ast.Exp typeCheck(Ast.EString eString) {
        return  eString;
    }

    public Ast.Exp typeCheck(Ast.EBool eBool) {
        return eBool;
    }


    public Ast.Exp typeCheck(Ast.EId eId) {
        Ast.Type type = context.lookupLatest(eId.name());
        if(type != null) {
            eId = new Ast.EId(eId.name(), type, eId.pos());
            return eId;
        } else {
            throw new TypeException("Variable " + eId.name() + " is not declared", eId.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.ECmp eCmp) {
        Ast.Exp left = typeCheck(eCmp.left());
        Ast.Exp right = typeCheck(eCmp.right());
        System.out.println("LEEEFT " + left);
        Ast.Type leftType = left.type();
        Ast.Type rightType = right.type();

        // Int and double can be compared
        if((leftType == Ast.Type.TDouble || leftType == Ast.Type.TInt)
                && (rightType == Ast.Type.TDouble || rightType == Ast.Type.TInt)) {
            return new Ast.ECmp(left, right, eCmp.op(), Ast.Type.TBool, eCmp.pos()); // Return the original expression with the same type
        } else {
            throw new TypeException("Cannot compare type " + leftType + " with type " + rightType, eCmp.pos());
        }
    }


}
