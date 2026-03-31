package com.example.minilang.codegen;

import java.util.HashSet;
import com.example.minilang.ast.Ast;


public class FunctionCodeGen {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private HashSet<String> declaredVariables;
    private Environment environment;
    private StringBuilder globals;
    private HashSet<String> functionVariables;
    private StatementCodeGen stmtGen;

    public FunctionCodeGen(StringBuilder sb, Environment environment, StringBuilder globals, HashSet<String> functionVariables, StatementCodeGen stmtGen) {

        this.sb = sb;
        this.environment = environment;
        this.globals = globals;
        this.functionVariables = functionVariables;
        this.stmtGen = stmtGen;
    }

    public void generateFunction(Ast.Func function) {


        Helper helper = new Helper();

        sb.append("define " + helper.convertType(function.returnType()) + " @" + function.name() + "(");

        if(function.params().isEmpty()){
            sb.append(") {\nentry:\n");
        }

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

        stmtGen.generateStatement(function.body());
        if(function.returnType() instanceof Ast.TUnknown) {
            sb.append("  ret void\n");
        }
        sb.append("\n}\n");
    }
}
