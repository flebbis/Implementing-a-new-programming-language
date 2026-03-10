package com.example.minilang;

public class CodeGenerator {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private final StringBuilder sb;

    public CodeGenerator(StringBuilder sb) {
        this.sb = sb;
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generate(Ast.Program program) {
        for (Ast.Stmt statement : program.stmts()) {
            StatementCodeGen stmtGen = new StatementCodeGen(sb);
            stmtGen.generateStatement(statement);
        }

        for (Ast.Func function : program.functions()) {
            System.out.println("Functions found: " + program.functions().size());
            FunctionCodeGen funcGen = new FunctionCodeGen(sb);
            funcGen.generateFunction(function);
        }

    }

}