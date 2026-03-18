package com.example.minilang;

import java.util.HashSet;

public class FunctionCodeGen {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private HashSet<String> declaredVariables;

    public FunctionCodeGen(StringBuilder sb, HashSet<String> declaredVariables) {

        this.sb = sb;
        this.declaredVariables = declaredVariables;
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generateFunction(Ast.Func function) {

        Helper helper = new Helper();

        sb.append("define " + helper.convertType(function.returnType()) + " @" + function.name() + "(");
        int i = 0;
        for (Ast.Arg arg : function.params()) {
            if (i < function.params().size()-1){
                sb.append(helper.convertType(arg.type()) + " %" + arg.name() + ", ");
            } else {
                sb.append(helper.convertType(arg.type()) + " %" + arg.name() + ") {\nentry:\n");
            }
            i++;

        }

        StatementCodeGen stmtGen = new StatementCodeGen(sb, declaredVariables);
        stmtGen.generateStatement(function.body());
        sb.append("\n}\n");
    }
}