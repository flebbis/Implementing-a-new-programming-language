package com.example.minilang.codegen;

import java.util.HashSet;
import com.example.minilang.ast.Ast;


public class CodeGenerator {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private final StringBuilder sb;
    private HashSet<String> declaredVariables = new HashSet<>();

    public CodeGenerator(StringBuilder sb) {
        this.sb = sb;
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generate(Ast.Program program) {
        sb.append("define void @main() {\n");
        sb.append("entry:\n");

        for (Ast.Stmt statement : program.stmts()) {
            StatementCodeGen stmtGen = new StatementCodeGen(sb, declaredVariables);
            stmtGen.generateStatement(statement);
        }

        sb.append("  ret void\n");
        //sb.append("  ret i32 0\n");
        sb.append("}\n\n");

        for (Ast.Func function : program.functions()) {
            System.out.println("Functions found: " + program.functions().size());
            FunctionCodeGen funcGen = new FunctionCodeGen(sb, declaredVariables);
            funcGen.generateFunction(function);
        }



    }

}
