package com.example.minilang;
import java.util.List;

import com.example.minilang.ast.Ast;

public class CodeGenerator {
    StringBuilder sb = new StringBuilder();

    public String CodeGeneratorC(String name, Ast.Program program) { // name is the name of the source file, but is really unnecessary if only wokring with one file
        // Output LLVM IR boilerplate
        println("declare i32 @printf(i8*, ...)");
        println("declare double @pow(double, double)");
        println("@.fmt.int = private constant [4 x i8] c\"%d\\0A\\00\"");
        println("@.fmt.double = private constant [4 x i8] c\"%f\\0A\\00\"");
        println("@.fmt.string = private constant [4 x i8] c\"%s\\0A\\00\"");
        println("@.fmt.newline = private constant [2 x i8] c\"\\0A\\00\"");

        // Generate code for each function
        LabelGenerator blockGenerator = new LabelGenerator();
        List<Ast.Func> functions = program.functions();
        DebugMetaData debugMetaData = new DebugMetaData(name);
        FunctionCodeGen functionCodeGen = new FunctionCodeGen(sb, blockGenerator, functions, debugMetaData);

        for (Ast.Func func : functions) {
            functionCodeGen.codeGenFunDef(func);
        }  

        sb.append(debugMetaData.emit());

        return sb.toString();
    }

    private void println(String s) {
    // System.out.println(s); // turn on for debugging
    sb.append(s);
    sb.append('\n');
  }

}