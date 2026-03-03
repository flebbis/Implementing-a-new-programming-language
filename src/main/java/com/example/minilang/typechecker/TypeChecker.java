package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.List;

public class TypeChecker {

    private List<Signature> signatures = new ArrayList<>();
    StatementTypeChecker statementTypeChecker = new StatementTypeChecker();

    public void typeCheck(Ast.Program program) {
        extractFunctionSignatures(program.functions()); // Begin with extracting function signatures
        typeCheckStatements(program.stmts()); // Then type check the statements
    }

    private void typeCheckStatements(List<Ast.Stmt> statements) {
        for(Ast.Stmt stmt : statements) {
            statementTypeChecker.typeCheck(stmt);
        }
    }


    private void extractFunctionSignatures(List<Ast.Func> functions) {
        for(Ast.Func func : functions) {
            String name = func.name();
            Ast.Type returnType = func.returnType();
            List<Ast.Type> paramTypes = new ArrayList<>();
            for(int i = 0; i < func.params().size(); i++) {
                paramTypes.add(func.params().get(i).type());
            }
            signatures.add(new Signature(name, returnType, paramTypes));
        }
    }
}
