package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatementTypeChecker {

    private Context context;
    private ExpressionTypeChecker expressionTypeChecker;
    private String currentFunction;
    private HashMap<String, Signature> functionSignatures;

    public StatementTypeChecker(Context context, HashMap<String, Signature>  functionSignatures) {
        this.context = context;
        this.functionSignatures = functionSignatures;
        this.expressionTypeChecker = new ExpressionTypeChecker(context, functionSignatures);
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
        context.pushNewScope();
        Ast.Exp condition = expressionTypeChecker.typeCheck(stmt.condition());
        if(condition.type() != Ast.Type.TBool) {
            throw new TypeException("Condition of while loop must be of type bool", condition.pos());
        }

        List<Ast.Stmt> bodyStmts = new ArrayList<>();
        // Check body
        if(!(stmt.body() instanceof Ast.SBlock)) {
            throw new TypeException("Body of while loop must be a block statement", stmt.body().pos());
        }
        for(Ast.Stmt s : ((Ast.SBlock) stmt.body()).statements()) {
            bodyStmts.add(typeCheck(s));
        }
        context.popScope();
        return new Ast.SWhile(condition, new Ast.SBlock(bodyStmts, stmt.body().pos()), stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SDo stmt) {
        // Type check the condition and body of the do loop
        // Ensure the condition is of type bool
        // Return an annotated SDo statement
        return stmt; // Placeholder
    }

    public Ast.Stmt typeCheck(Ast.SReturn stmt) {
        Ast.Exp value = expressionTypeChecker.typeCheck(stmt.value());
        if(currentFunction == null) {
            throw new TypeException("Return statement not inside a function", stmt.pos());
        }
        Signature signature = functionSignatures.get(currentFunction);
        if(signature.returnType != value.type()) {
            // Allow implicit conversion from int to double
            if(signature.returnType == Ast.Type.TDouble && value.type() == Ast.Type.TInt) {
                value = new Ast.EDInt(value, value.type(), value.pos());
            } else {
                throw new TypeException("Function returns type " + value.type() + ", does not match declared function return type " + signature.returnType, stmt.pos());
            }
        }

        return new Ast.SReturn(value, stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SIf stmt) {
        // Type check the condition and both branches of the if statement
        // Ensure the condition is of type bool
        // Return an annotated SIf statement
        return stmt; // Placeholder
    }

    public void setCurrentFunction(String currentFunction) {
        this.currentFunction = currentFunction;
    }
}
