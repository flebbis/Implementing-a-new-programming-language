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


    public Ast.Stmt typeCheck(Ast.SInit sInit) {
        Ast.Exp value = expressionTypeChecker.typeCheck(sInit.value());
        Ast.Type typeToCheck = sInit.type();

        // Logical Check: Is this a redeclaration or an assignment?
        // We look up if the variable exists in the current scope chain.
        Ast.Type existingType = context.lookupLatest(sInit.name());

        // If the variable exists and this is an implicit statement (e.g. "x = 5" not "int x = 5"),
        // treat it as an Assignment to the existing variable.
        if (existingType != null && typeToCheck == Ast.Type.TUnknown) {

            // Allow TUnknown values to bypass checks during inference pass
            if (existingType != value.type() && value.type() != Ast.Type.TUnknown) {
                if (existingType == Ast.Type.TDouble && value.type() == Ast.Type.TInt) {
                    // Implicit cast
                    value = new Ast.EDInt(value, value.type(), value.pos());
                } else {
                    throw new TypeException("Cannot assign value of type " + value.type() + " to variable " + sInit.name() + " of type " + existingType, sInit.pos());
                }
            }

            // Transform this node into an Assignment Expression Statement
            Ast.EAss assign = new Ast.EAss(sInit.name(), value, Ast.AssOp.ASSIGN, existingType, sInit.pos());
            return new Ast.SExp(assign, sInit.pos());
        }

        // If we reach here, it is a new Declaration.

        // Infer type if unknown
        if (typeToCheck == Ast.Type.TUnknown) {
            typeToCheck = value.type();
        }

        // Verify types match for explicit declarations
        // Allow TUnknown values to bypass checks during inference pass
        if (typeToCheck != value.type() && value.type() != Ast.Type.TUnknown) {
            if (typeToCheck == Ast.Type.TDouble && value.type() == Ast.Type.TInt) {
                value = new Ast.EDInt(value, value.type(), value.pos());
            } else {
                throw new TypeException("Incorrect initialisation of type " + value.type() + ", expected " + typeToCheck, value.pos());
            }
        }

        context.pushToCurrentScope(sInit.name(), typeToCheck);
        return new Ast.SInit(typeToCheck, sInit.name(), value, sInit.pos());
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

        // Inference: If return type is TUnknown, infer it from the return value
        if (signature.returnType == Ast.Type.TUnknown && value.type() != Ast.Type.TUnknown) {
            signature.setReturnType(value.type());
        }

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
        Ast.Exp condition = expressionTypeChecker.typeCheck(stmt.condition());

        if(condition.type() != Ast.Type.TBool) {
            throw new TypeException("Condition of if statement must be of type bool", stmt.condition().pos());
        }

        // Check then branch
        context.pushNewScope();
        if(!(stmt.thenBranch() instanceof Ast.SBlock)) {
            throw new TypeException("Then branch of if statement must be a block statement", stmt.thenBranch().pos());
        }

        Ast.Stmt thenStmt = typeCheck(stmt.thenBranch()); // Check for any type errors in the then branch before checking the else branch (for better error messages)

        Ast.Stmt elseStmt = null;
        if(stmt.elseBranch() != null) {
            if (!(stmt.elseBranch() instanceof Ast.SBlock)) {
                throw new TypeException("Else branch of if statement must be a block statement", stmt.elseBranch().pos());
            }
            elseStmt = typeCheck(stmt.elseBranch());
        }

        return new Ast.SIf(condition, thenStmt, elseStmt, stmt.pos()); // Placeholder
    }

    public Ast.SBlock typeCheck(Ast.SBlock stmt) {
        context.pushNewScope();
        List<Ast.Stmt> checkedStmts = new ArrayList<>();
        for(Ast.Stmt s : stmt.statements()) {
            checkedStmts.add(typeCheck(s));
        }
        context.popScope();

        return new Ast.SBlock(checkedStmts, stmt.pos());
    }

    public void setCurrentFunction(String currentFunction) {
        this.currentFunction = currentFunction;
    }
}
