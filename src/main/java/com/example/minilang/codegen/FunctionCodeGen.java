package com.example.minilang.codegen;

import java.util.HashSet;
import com.example.minilang.ast.Ast;


public class FunctionCodeGen {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private HashSet<String> declaredVariables;
    private StringBuilder globals;
    private HashSet<String> functionVariables;

    public FunctionCodeGen(StringBuilder sb, HashSet<String> declaredVariables, StringBuilder globals, HashSet<String> functionVariables) {

        this.sb = sb;
        this.declaredVariables = declaredVariables;
        this.globals = globals;
        this.functionVariables = functionVariables;
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
            functionVariables.add(arg.name());
            i++;

        }

        StatementCodeGen stmtGen = new StatementCodeGen(sb, declaredVariables, globals, functionVariables);
        stmtGen.generateStatement(function.body());
        sb.append("\n}\n");
    }
}
