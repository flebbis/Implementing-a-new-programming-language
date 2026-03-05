package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeChecker {

    private HashMap<String, Signature> functionSignatures = new HashMap<>();
    private StatementTypeChecker statementTypeChecker;
    private Context context;

    public TypeChecker() {
        this.context = new Context();
        this.statementTypeChecker = new StatementTypeChecker(context, functionSignatures);
    }

    public Ast.Program typeCheck(Ast.Program program) {
        List<Ast.Func> funcs = extractFunctionSignatures(program.functions()); // Begin with extracting function signatures
        List<Ast.Stmt> stmts = typeCheckStatements(program.stmts()); // Then type check the statements
        return new Ast.Program(stmts, funcs);
    }

    private List<Ast.Stmt> typeCheckStatements(List<Ast.Stmt> statements) {
        List<Ast.Stmt> stmts = new ArrayList<>();
        for(Ast.Stmt stmt : statements) {
            stmts.add(statementTypeChecker.typeCheck(stmt));
        }
        return stmts;
    }

    // TODO: infer this
    private List<Ast.Func> extractFunctionSignatures(List<Ast.Func> functions) {
        List<Ast.Func> funcs = new ArrayList<>();
        for(Ast.Func func : functions) {
            String name = func.name();
            Ast.Type returnType = func.returnType();
            List<Ast.Type> paramTypes = new ArrayList<>();
            for(int i = 0; i < func.params().size(); i++) {
                paramTypes.add(func.params().get(i).type());
            }
            functionSignatures.put(name, new Signature(name, returnType, paramTypes));
            funcs.add(new Ast.Func(name, func.params(), returnType, func.body(), func.pos()));
        }
        return funcs;
    }
}
