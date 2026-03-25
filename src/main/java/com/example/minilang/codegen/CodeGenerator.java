package com.example.minilang.codegen;

import java.util.HashSet;
import com.example.minilang.ast.Ast;


public class CodeGenerator {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private final StringBuilder sb;
    private Environment environment;
    private final StringBuilder globals;
    private final HashSet<String> functionVariables = new HashSet<>();

    public CodeGenerator(StringBuilder sb, StringBuilder globals) {
        this.environment = new Environment();
        this.sb = sb;
        this.globals = globals;
        // Initialize any necessary state here (e.g., symbol tables, label generators, etc.)
    }

    public void generate(Ast.Program program) {
        //External functions
        sb.append("declare double @pow(double, double)\n");
        sb.append("declare i32 @printf(i8*, ...)\n");
        sb.append("@.fmt.int = private constant [4 x i8] c\"%d\\0A\\00\"\n");
        sb.append("@.fmt.double = private constant [4 x i8] c\"%f\\0A\\00\"\n");
        sb.append("@.fmt.string = private constant [4 x i8] c\"%s\\0A\\00\"\n");
        sb.append("declare i8* @int_to_string(i32)\n");
        sb.append("declare i8* @double_to_string(double)\n");
        sb.append("declare i8* @bool_to_string(i1)\n");
        sb.append("declare i8* @string_concat(i8*, i8*)\n");




        sb.append("define void @main() {\n");
        sb.append("entry:\n");
        StatementCodeGen stmtGen = new StatementCodeGen(sb, environment, globals, functionVariables);
        for (Ast.Stmt statement : program.stmts()) {
            stmtGen.generateStatement(statement);
        }

        sb.append("  ret void\n");
        //sb.append("  ret i32 0\n");
        sb.append("}\n\n");

        for (Ast.Func function : program.functions()) {
            System.out.println("Functions found: " + program.functions().size());
            FunctionCodeGen funcGen = new FunctionCodeGen(sb, environment, globals, functionVariables, stmtGen);
            funcGen.generateFunction(function);
        }



    }

}
