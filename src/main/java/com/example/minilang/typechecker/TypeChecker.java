package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeChecker {

    private HashMap<String, Signature> functionSignatures = new HashMap<>();
    private StatementTypeChecker statementTypeChecker;
    private Context context;
    private Context inferenceContext;

    public TypeChecker() {
        this.context = new Context();
        this.inferenceContext = new Context();
        this.statementTypeChecker = new StatementTypeChecker(context, functionSignatures, inferenceContext);
    }

    public Ast.Program typeCheck(Ast.Program program) {
        // 1. Extract signatures (initially TUnknown)
        List<Ast.Func> rawFuncs = extractFunctionSignatures(program.functions());

        // --- INFERENCE PHASE ---
        // We use a temporary Context and Checker to run the logic just for the side-effects
        // of updating the 'functionSignatures' map. We discard the AST produced here.
        Context tempContext = new Context();
        StatementTypeChecker tempChecker = new StatementTypeChecker(tempContext, functionSignatures, new Context());

        // Swap to temp environment
        Context realContext = this.context;
        StatementTypeChecker realChecker = this.statementTypeChecker;
        this.context = tempContext;
        this.statementTypeChecker = tempChecker;

        // Pass 1: Scan statements -> Infers Parameter Types from calls
        typeCheckStatements(program.stmts());

        // Pass 2: Scan bodies -> Infers Return Types from return statements
        // (Now that params are known from Pass 1, we can successfully type check the body)
        context.popScope();

        checkFunctionBodies(rawFuncs);

        // --- GENERATION PHASE ---
        // Restore real environment
        this.context = realContext;
        this.statementTypeChecker = realChecker;

        statementTypeChecker.updateInferenceContext(tempContext);

        // Pass 3: Re-scan statements -> Generates final AST with correct ECall types
        List<Ast.Stmt> stmts = typeCheckStatements(program.stmts());

        // Pass 4: Re-scan bodies -> Generates final AST for functions
        List<Ast.Func> checkedFuncs = checkFunctionBodies(rawFuncs);

        return new Ast.Program(stmts, checkedFuncs);
    }

    private List<Ast.Stmt> typeCheckStatements(List<Ast.Stmt> statements) {
        List<Ast.Stmt> stmts = new ArrayList<>();
        for(Ast.Stmt stmt : statements) {
            if(stmt == null) {
                continue; // for some reason this happened
            }
            stmts.add(statementTypeChecker.typeCheck(stmt));
        }
        return stmts;
    }

    private List<Ast.Func> extractFunctionSignatures(List<Ast.Func> functions) {
        List<Ast.Func> funcs = new ArrayList<>();
        for(Ast.Func func : functions) {
            String name = func.name();
            Ast.Type returnType = func.returnType();

            List<Ast.Type> paramTypes = new ArrayList<>();
            for(Ast.Arg arg : func.params()) {
                paramTypes.add(arg.type());
            }

            functionSignatures.put(name, new Signature(name, returnType, paramTypes));
            funcs.add(func);
        }
        return funcs;
    }

    private List<Ast.Func> checkFunctionBodies(List<Ast.Func> functions) {
        List<Ast.Func> checkedFuncs = new ArrayList<>();

        for(Ast.Func func : functions) {
            context.pushNewScope();
            String name = func.name();
            statementTypeChecker.setCurrentFunction(name);
            Signature sig = functionSignatures.get(name);

            // Use types from Signature (which are now updated after inference passes)
            List<Ast.Arg> currentParams = new ArrayList<>();
            for(int i = 0; i < func.params().size(); i++) {
                Ast.Type type = sig.paramTypes.get(i);
                Ast.Arg oldArg = func.params().get(i);

                context.pushToCurrentScope(oldArg.name(), type);
                currentParams.add(new Ast.Arg(oldArg.name(), type, oldArg.pos()));
            }

            if(!(func.body() instanceof Ast.SBlock)) {
                throw new TypeException("Function body must be a block statement", func.body().pos());
            }

            List<Ast.Stmt> bodyStmts = new ArrayList<>();
            for(Ast.Stmt stmt : ((Ast.SBlock) func.body()).statements()) {
                bodyStmts.add(statementTypeChecker.typeCheck(stmt));
            }

            checkedFuncs.add(new Ast.Func(name, currentParams, sig.returnType, new Ast.SBlock(bodyStmts, func.body().pos()), func.pos()));
            context.popScope();
        }
        return checkedFuncs;
    }
}
