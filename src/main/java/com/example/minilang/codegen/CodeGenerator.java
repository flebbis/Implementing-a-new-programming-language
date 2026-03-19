package com.example.minilang.codegen;

import java.util.HashSet;
import com.example.minilang.ast.Ast;


public class CodeGenerator {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private final StringBuilder sb;
    private HashSet<String> declaredVariables = new HashSet<>();
    private final StringBuilder globals;

    public CodeGenerator(StringBuilder sb, StringBuilder globals) {
        this.sb = sb;
        this.globals = globals;
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generate(Ast.Program program) {
        //External functions
        sb.append("declare double @pow(double, double)\n");


        sb.append("define void @main() {\n");
        sb.append("entry:\n");

        for (Ast.Stmt statement : program.stmts()) {
            StatementCodeGen stmtGen = new StatementCodeGen(sb, declaredVariables, globals);
            stmtGen.generateStatement(statement);
        }

        sb.append("  ret void\n");
        //sb.append("  ret i32 0\n");
        sb.append("}\n\n");

        for (Ast.Func function : program.functions()) {
            System.out.println("Functions found: " + program.functions().size());
            FunctionCodeGen funcGen = new FunctionCodeGen(sb, declaredVariables, globals);
            funcGen.generateFunction(function);
        }



    }

}
