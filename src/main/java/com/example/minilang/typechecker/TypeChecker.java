package com.example.minilang.typechecker;

import com.example.minilang.Pos;
import com.example.minilang.TypeConverter;
import com.example.minilang.TypeReplacementSuggestion;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.minilang.InferenceSuggestion;
import com.sun.source.doctree.DocTree;

public class TypeChecker {

    private HashMap<String, Signature> functionSignatures = new HashMap<>();
    private StatementTypeChecker statementTypeChecker;
    private Context context;
    private Context inferenceContext;
    private List<InferenceSuggestion> inferenceSuggestions = new ArrayList<>();
    private List<TypeReplacementSuggestion> typeReplacementSuggestions = new ArrayList<>();
    private HashMap<String, List<String>> functionBindings = new HashMap<>();
    private HashMap<String, String> functionReturnBindingIds = new HashMap<>();
    private HashMap<String, List<Boolean>> functionParamExplicit = new HashMap<>();





    public TypeChecker() {
        this.context = new Context();
        this.inferenceContext = new Context();
        this.statementTypeChecker = new StatementTypeChecker(context, functionSignatures, inferenceContext, inferenceSuggestions, typeReplacementSuggestions, functionBindings,functionReturnBindingIds);
    }

    public Ast.Program typeCheck(Ast.Program program) {
        // 1. Extract signatures (initially TUnknown)
        List<Ast.Func> rawFuncs = extractFunctionSignatures(program.functions());

        // --- INFERENCE PHASE ---
        // We use a temporary Context and Checker to run the logic just for the side-effects
        // of updating the 'functionSignatures' map. We discard the AST produced here.
        Context tempContext = new Context();
        // InferenceSuggestion list is the real list for inference pass, the other pass will use a temp list

        HashMap<String, List<String>> tempFunctionBindings = new HashMap<>();
        HashMap<String, String> tempFunctionReturnBindingIds = new HashMap<>();
        List<InferenceSuggestion> tempInferenceSuggestions = new ArrayList<>();
        StatementTypeChecker tempChecker = new StatementTypeChecker(tempContext, functionSignatures, new Context(), tempInferenceSuggestions, typeReplacementSuggestions, tempFunctionBindings,  tempFunctionReturnBindingIds);

        // Swap to temp environment
        Context realContext = this.context;
        StatementTypeChecker realChecker = this.statementTypeChecker;
        this.context = tempContext;
        this.statementTypeChecker = tempChecker;

        // Pass 1: Scan statements -> Infers Parameter Types from calls
        registerFunctionParamBindings(tempContext, rawFuncs, functionBindings);
        typeCheckStatements(program.stmts());
        reconcileParamBindingsFromDependencies(tempContext, functionBindings, rawFuncs);

        List<Ast.Func> checkedFuncsPass1 = checkFunctionBodies(rawFuncs);



        //TODO: remove???
//        context.popScope();
        if(!tempInferenceSuggestions.isEmpty()) {
            inferenceSuggestions.addAll(tempInferenceSuggestions);
        }

        // Restore real environment
        this.context = realContext;
        this.statementTypeChecker = realChecker;

        // Update the real StatementTypeChecker with the inferred types before the final pass
        statementTypeChecker.updateInferenceContext(tempContext);

        registerFunctionParamBindings(context, rawFuncs, functionBindings);
        registerFunctionReturnBindings(checkedFuncsPass1);
        List<Ast.Stmt> stmts = typeCheckStatements(program.stmts());
        List<Ast.Func> checkedFuncs = checkFunctionBodies(checkedFuncsPass1);

        context.printBindings();


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

            List<Boolean> explicitFlags = new ArrayList<>();
            List<Ast.Type> paramTypes = new ArrayList<>();
            for(Ast.Arg arg : func.params()) {
                paramTypes.add(arg.type());
                explicitFlags.add(!(arg.type() instanceof Ast.TUnknown));
                System.err.println(
                        "[EXTRACT PARAM] func=" + name
                                + " paramName=" + arg.name()
                                + " astType=" + TypeConverter.typeToString(arg.type())
                                + " explicitFlag=" + (!(arg.type() instanceof Ast.TUnknown))
                );
            }
            functionParamExplicit.put(name, explicitFlags);


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


            // Return type inference notification
            inferReturnType(func, sig, name);

            // Get canonical param binding IDs created in registerFunctionParamBindings(...)
            List<String> paramBindingIds = functionBindings.get(name);
            if (paramBindingIds == null || paramBindingIds.size() != func.params().size()) {
                throw new TypeException("Missing parameter bindings for function " + name, func.pos());
            }

            List<Ast.Arg> currentParams = new ArrayList<>();
            int offSet = 0;

            for (int i = 0; i < func.params().size(); i++) {
                Ast.Arg oldArg = func.params().get(i);
                Ast.Type inferredType = sig.paramTypes.get(i);

                // Suggest inferred type for untyped/changed params
                if (!oldArg.type().equals(inferredType)) {
                    Pos pos = new Pos(oldArg.pos().line, oldArg.pos().column + offSet);
                    addInferenceSuggestion(oldArg.name(), inferredType, pos);
                    offSet += TypeConverter.typeToString(inferredType).length() + 1;
                }

                // Reuse existing binding instead of creating a duplicate binding
                String paramBindingId = paramBindingIds.get(i);
                context.bindExistingToCurrentScope(oldArg.name(), paramBindingId);

                // Keep binding type in sync with final signature type
                context.updateBindingType(paramBindingId, inferredType);

                currentParams.add(new Ast.Arg(oldArg.name(), inferredType, oldArg.pos()));
            }

            if (!(func.body() instanceof Ast.SBlock)) {
                throw new TypeException("Function body must be a block statement", func.body().pos());
            }

            List<Ast.Stmt> bodyStmts = new ArrayList<>();
            for (Ast.Stmt stmt : ((Ast.SBlock) func.body()).statements()) {
                bodyStmts.add(statementTypeChecker.typeCheck(stmt));
            }

            checkedFuncs.add(new Ast.Func(
                    name,
                    currentParams,
                    sig.returnType,
                    new Ast.SBlock(bodyStmts, func.body().pos()),
                    func.pos()
            ));

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
    public List<TypeReplacementSuggestion> getTypeReplacementSuggestions() {return typeReplacementSuggestions;}

    private void registerFunctionParamBindings(
            Context ctx,
            List<Ast.Func> functions,
            HashMap<String, List<String>> targetBindings
    ) {
        targetBindings.clear();

        for (Ast.Func func : functions) {
            ctx.pushNewScope();
            String name = func.name();
            Signature sig = functionSignatures.get(name);

            List<String> paramBindingIds = new ArrayList<>();
            for (int i = 0; i < func.params().size(); i++) {
                Ast.Arg arg = func.params().get(i);
                Ast.Type inferredParamType = sig.paramTypes.get(i);

                boolean explicit = functionParamExplicit.get(name).get(i);
                System.err.println(
                        "[CREATE PARAM BINDING] func=" + name
                                + " index=" + i
                                + " paramName=" + arg.name()
                                + " declaredType=" + TypeConverter.typeToString(arg.type())
                                + " inferredType=" + TypeConverter.typeToString(inferredParamType)
                                + " explicit=" + explicit
                );
                String id = ctx.createBinding(
                        arg.name(),
                        Binding.Kind.PARAMETER,
                        arg.pos(),
                        arg.type(),
                        inferredParamType,
                        explicit
                );
                Binding created = ctx.getBinding(id);
                System.err.println(
                        "[CREATED PARAM BINDING] id=" + id
                                + " name=" + created.name
                                + " declaredType=" + TypeConverter.typeToString(created.declaredType)
                                + " inferredType=" + TypeConverter.typeToString(created.inferredType)
                                + " explicit=" + created.explicit
                );
                paramBindingIds.add(id);
            }

            targetBindings.put(name, paramBindingIds);
            ctx.popScope();
        }
    }
    private void registerFunctionReturnBindings(List<Ast.Func> functions) {
        for (Ast.Func func : functions) {
            String name = func.name();
            Signature sig = functionSignatures.get(name);

            String returnBindingId = context.createBinding(
                    name + "@return",
                    Binding.Kind.FUNCTION_RETURN,
                    func.pos(),
                    func.returnType(),
                    sig.returnType,
                    !(func.returnType() instanceof Ast.TUnknown)
            );

            functionReturnBindingIds.put(name, returnBindingId);
        }
    }
    private void reconcileParamBindingsFromDependencies(
            Context ctx,
            HashMap<String, List<String>> bindingsMap,
            List<Ast.Func> functions
    ) {
        // Build quick lookup: function -> params (for source positions and names)
        HashMap<String, List<Ast.Arg>> funcParams = new HashMap<>();
        for (Ast.Func f : functions) {
            funcParams.put(f.name(), f.params());
        }

        for (String funcName : bindingsMap.keySet()) {
            List<String> paramIds = bindingsMap.get(funcName);
            List<Ast.Arg> params = funcParams.get(funcName);
            if (paramIds == null || params == null) continue;

            for (int i = 0; i < paramIds.size() && i < params.size(); i++) {
                String paramId = paramIds.get(i);
                Binding param = ctx.getBinding(paramId);
                if (param == null) continue;

                Ast.Arg argAst = params.get(i);
                Ast.Type currentParamType = param.inferredType;

                for (String depId : param.dependencies) {
                    Binding dep = ctx.getBinding(depId);
                    if (dep == null || dep.inferredType instanceof Ast.TUnknown) continue;

                    Ast.Type depType = dep.inferredType;

                    // Same type => nothing to do
                    if (currentParamType.equals(depType)) {
                        break;
                    }

                    // If param is not explicit, infer immediately
                    if (!param.explicit) {
                        ctx.updateBindingType(paramId, depType);
                        functionSignatures.get(funcName).paramTypes.set(i, depType);
                        currentParamType = depType;
                        break;
                    }

                    // Param is explicit and mismatches a dependency:
                    // emit replacement suggestion so server can offer/cascade edit.
                    String currentTypeStr = TypeConverter.typeToString(currentParamType);
                    String newTypeStr = TypeConverter.typeToString(depType);

                    int line = argAst.pos().line;
                    int col = argAst.pos().column;

                    TypeReplacementSuggestion trs = new TypeReplacementSuggestion(
                            argAst.name(),      // name shown in UI
                            currentTypeStr,     // currently declared type
                            line,
                            col,
                            line,
                            col + currentTypeStr.length(),
                            newTypeStr
                    );

                    if (!typeReplacementSuggestions.contains(trs)) {
                        typeReplacementSuggestions.add(trs);
                    }

                    // Do not overwrite explicit param here; let user/server apply edit.
                    break;
                }
            }
        }
    }

}
