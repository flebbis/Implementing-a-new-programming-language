package com.example.minilang.typechecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.minilang.InferenceSuggestion;
import com.example.minilang.Pos;
import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;

public class TypeChecker {

    private HashMap<String, Signature> functionSignatures = new HashMap<>();
    private StatementTypeChecker statementTypeChecker;
    private Context context;
    private Context inferenceContext;
    private List<InferenceSuggestion> inferenceSuggestions = new ArrayList<>();

    public TypeChecker() {
        this.context = new Context();
        this.inferenceContext = new Context();
        this.statementTypeChecker = new StatementTypeChecker(context, functionSignatures, inferenceContext, inferenceSuggestions);
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
        StatementTypeChecker tempChecker = new StatementTypeChecker(tempContext, functionSignatures, new Context(), tempInferenceSuggestions);

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


        for (Ast.Func func : functions) {
            context.pushNewScope();
            String name = func.name();
            statementTypeChecker.setCurrentFunction(name);
            Signature sig = functionSignatures.get(name);

            inferReturnType(func, sig, name);

            // Use types from Signature (which are now updated after inference passes)
            List<Ast.Arg> currentParams = new ArrayList<>();


            for (int i = 0; i < func.params().size(); i++) {
                Ast.Type type = sig.paramTypes.get(i);

                // Inference suggestion
                if(!(func.params().get(i).type().equals(type))) {
                    Ast.Arg arg = func.params().get(i);
                    addInferenceSuggestion(name, type, arg.pos());
                }

                Ast.Arg oldArg = func.params().get(i);

                context.pushToCurrentScope(oldArg.name(), type);
                currentParams.add(new Ast.Arg(oldArg.name(), type, oldArg.pos()));
            }

            if (!(func.body() instanceof Ast.SBlock)) {
                throw new TypeException("Function body must be a block statement", func.body().pos());
            }

            List<Ast.Stmt> bodyStmts = new ArrayList<>();
            for (Ast.Stmt stmt : ((Ast.SBlock) func.body()).statements()) {
                bodyStmts.add(statementTypeChecker.typeCheck(stmt));
            }

            checkedFuncs.add(new Ast.Func(name, currentParams, sig.returnType,
                    new Ast.SBlock(bodyStmts, func.body().pos()), func.pos()));
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
            System.err.println("Inferred return type of function '" + name + "' as " + TypeConverter.typeToString(sig.returnType));
            addInferenceSuggestion(name, sig.returnType, func.pos());
        }

    }

    public List<InferenceSuggestion> getInferenceSuggestions() {
        return inferenceSuggestions;
    }

}
