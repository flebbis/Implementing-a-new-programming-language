package com.example.minilang.typechecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.minilang.*;
import com.example.minilang.ast.Ast;

public class TypeChecker {

    private HashMap<String, Signature> functionSignatures = new HashMap<>();
    private StatementTypeChecker statementTypeChecker;
    private Context context;
    private Context inferenceContext;
    private List<InferenceSuggestion> inferenceSuggestions = new ArrayList<>();
    private List<TypeError> typeErrors = new ArrayList<>();
    private List<TypeReplacementSuggestion>  typeReplacementSuggestions = new ArrayList<>();

    public TypeChecker() {
        this.context = new Context();
        this.inferenceContext = new Context();
        this.statementTypeChecker = new StatementTypeChecker(context, functionSignatures, inferenceContext, inferenceSuggestions, typeReplacementSuggestions);
    }

    public Ast.Program typeCheck(Ast.Program program) {
        // 1. Extract signatures (initially TUnknown)
        List<Ast.Func> rawFuncs = extractFunctionSignatures(program.functions());

        // --- INFERENCE PHASE ---
        // We use a temporary Context and Checker to run the logic just for the side-effects
        // of updating the 'functionSignatures' map. We discard the AST produced here.
        Context tempContext = new Context();
        // InferenceSuggestion list is the real list for inference pass, the other pass will use a temp list

        List<InferenceSuggestion> tempInferenceSuggestions = new ArrayList<>();
        List<TypeReplacementSuggestion> tempTypeReplacementSuggestions = new ArrayList<>();
        StatementTypeChecker tempChecker = new StatementTypeChecker(tempContext, functionSignatures, new Context(), tempInferenceSuggestions, tempTypeReplacementSuggestions);
        // Swap to temp environment
        Context realContext = this.context;
        StatementTypeChecker realChecker = this.statementTypeChecker;
        this.context = tempContext;
        this.statementTypeChecker = tempChecker;

        // Pass 1: Scan statements -> Infers Parameter Types from calls
        typeCheckStatements(program.stmts());
        List<Ast.Func> checkedFuncsPass1 = checkFunctionBodies(rawFuncs);



        // --- GENERATION PHASE ---
        context.popScope();
        if(!tempInferenceSuggestions.isEmpty()) {
            inferenceSuggestions.addAll(tempInferenceSuggestions);
        }

        // Restore real environment
        this.context = realContext;
        this.statementTypeChecker = realChecker;

        // Update the real StatementTypeChecker with the inferred types before the final pass
        statementTypeChecker.updateInferenceContext(tempContext);

        List<Ast.Stmt> stmts = typeCheckStatements(program.stmts());
        List<Ast.Func> checkedFuncs = checkFunctionBodies(checkedFuncsPass1);

        return new Ast.Program(stmts, checkedFuncs);
    }

    private List<Ast.Stmt> typeCheckStatements(List<Ast.Stmt> statements) {
        List<Ast.Stmt> stmts = new ArrayList<>();
        for(Ast.Stmt stmt : statements) {
            if(stmt == null) {
                continue; // for some reason this happened
            }
            try {
                stmts.add(statementTypeChecker.typeCheck(stmt));
            }
            catch (TypeException e) {
                addTypeError(e);
            }
        }
        return stmts;
    }

    private List<Ast.Func> extractFunctionSignatures(List<Ast.Func> functions) {
        List<Ast.Func> funcs = new ArrayList<>();
        for (Ast.Func func : functions) {

            String name = func.name();
            if(functionSignatures.containsKey(name)) {
                addTypeError(new TypeException("Duplicate function name: " + name, func.pos()));
                continue;
            }

            if(IllegalIDs.illegalIDs.contains(name)) {
                addTypeError(new TypeException("Illegal function name: " + name, func.pos()));
                continue;
            }

            Ast.Type returnType = func.returnType();

            List<Ast.Type> paramTypes = new ArrayList<>();
            for(Ast.Arg arg : func.params()) {
                if(arg.type() instanceof Ast.TUnknown) {
                    arg = new Ast.Arg(arg.name(), new Ast.TUnresolved(arg.name(), new ArrayList<>()), arg.pos());
                }
                paramTypes.add(arg.type());
            }

            functionSignatures.put(name, new Signature(name, returnType, paramTypes));
            funcs.add(func);
        }
        return funcs;
    }

    private List<Ast.Func> checkFunctionBodies(List<Ast.Func> functions) {
        List<Ast.Func> checkedFuncs = new ArrayList<>();


        for (Ast.Func func : functions) {
            context.pushNewScope();
            String name = func.name();
            statementTypeChecker.setCurrentFunction(name);
            Signature sig = functionSignatures.get(name);

            inferReturnType(func, sig, name);

            // Use types from Signature (which are now updated after inference passes)
            List<Ast.Arg> currentParams = new ArrayList<>();


            int offSet = 0;

            // Check parameter types and add inference suggestions if needed
            for (int i = 0; i < func.params().size(); i++) {
                Ast.Type callType = sig.paramTypes.get(i);


                Ast.Arg arg = func.params().get(i);


                // Inference suggestion
                if (!(TypeUtils.equalTypes(arg.type(), callType))) {
                    if(!TypeConverter.typeToString(callType).equals("unknown") && !TypeConverter.typeToString(callType).contains("or")) {
                        Pos pos = new Pos(arg.pos().line, arg.pos().column + offSet);
                        addInferenceSuggestion(name, callType, pos);
                        //offSet += TypeConverter.typeToString(callType).length() + 1;
                    }
                }

                context.pushToCurrentScope(arg.name(), callType, arg.pos());
                currentParams.add(new Ast.Arg(arg.name(), callType, arg.pos()));
            }

            if (!(func.body() instanceof Ast.SBlock)) {
                addTypeError(new TypeException("Function body must be a block statement", func.body().pos()));
            }

            if(!(sig.returnType instanceof Ast.TUnknown)) {
                if(!definitelyReturnsStmt(func.body())) {
                    addTypeError(new TypeException("Missing return statement in function " + name, func.pos()));
                }
            }

            // Check body of statements
            List<Ast.Stmt> bodyStmts = new ArrayList<>();
            for (Ast.Stmt stmt : ((Ast.SBlock) func.body()).statements()) {
                try {
                    bodyStmts.add(statementTypeChecker.typeCheck(stmt));
                } catch (TypeException e) {
                    addTypeError(e);
                }
            }

            boolean calledFunc = statementTypeChecker.getCalledFunctions().contains(name);

            checkedFuncs.add(new Ast.Func(name, currentParams, sig.returnType,
                    new Ast.SBlock(bodyStmts, func.body().pos()), calledFunc, func.pos()));
            context.popScope();
        }
        return checkedFuncs;
    }

    private void addInferenceSuggestion(String name, Ast.Type type, Pos arg) {
        inferenceSuggestions.add(new InferenceSuggestion(
                name,
                TypeConverter.typeToString(type),
                arg.line,
                arg.column,
                arg.line,
                arg.column + name.length(),
                "Suggestion: " + TypeConverter.typeToString(type) + " " + name.length()));
    }

    private void inferReturnType(Ast.Func func, Signature sig, String name) {
        // Is inference, add type

        if(sig.isInference) {
            // Add inference suggestion if the return type is not unknown or if it is a union type (contains "or")
            if(!TypeConverter.typeToString(sig.returnType).equals("unknown") && !TypeConverter.typeToString(sig.returnType).contains("or")) {
                addInferenceSuggestion(name, sig.returnType, func.pos());
            }
        }

    }


private boolean definitelyReturns(List<Ast.Stmt> statements) {
    for (Ast.Stmt stmt : statements) {
        if (definitelyReturnsStmt(stmt)) {
            return true; // once we hit a definite return, we're done
        }
    }
    return false;
}

private boolean definitelyReturnsStmt(Ast.Stmt stmt) {
    if (stmt instanceof Ast.SReturn) {
        return true;
    }
    
    if (stmt instanceof Ast.SIf ifStmt) {
        // only definite if BOTH branches exist AND both definitely return
        return ifStmt.elseBranch() != null
            && definitelyReturnsStmt(ifStmt.thenBranch())
            && definitelyReturnsStmt(ifStmt.elseBranch());
    }
    
    if (stmt instanceof Ast.SBlock block) {
        return definitelyReturns(block.statements());
    }
    
    // loops, assignments, prints etc — don't guarantee a return
    return false;
}


    private void addTypeError(TypeException e) {
        if(!typeErrors.contains(TypeCheckerServer.extractErrorInfo(e))) {
            typeErrors.add(TypeCheckerServer.extractErrorInfo(e));
        }
    }

    public List<TypeError> getTypeErrors() {
        return typeErrors;
    }
    public List<InferenceSuggestion> getInferenceSuggestions() {
        return inferenceSuggestions;
    }

    public List<TypeReplacementSuggestion> getTypeReplacementSuggestions() {
        return typeReplacementSuggestions;
    }

}
