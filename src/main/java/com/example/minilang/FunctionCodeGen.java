package com.example.minilang;

public class FunctionCodeGen {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private final StringBuilder sb;

    public FunctionCodeGen(StringBuilder sb) {

        this.sb = sb;
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generateFunction(Ast.Func function) {

        sb.append("define " + function.returnType() + " @" + function.name() + "(");
        int i = 0;
        for (Ast.Arg arg : function.params()) {
            if (i < function.params().size()-1){
                sb.append(arg.type() + " %" + arg.name() + ", ");
            } else {
                sb.append(arg.type() + " %" + arg.name() + ")");
            }
            
        }

        // Here you would implement the logic to generate LLVM IR code for a function definition.
        // This would involve:
        // 1. Generating the function signature (name, parameters, return type)
        // 2. Generating the function body by traversing the AST of the function's body statements.
    }
}