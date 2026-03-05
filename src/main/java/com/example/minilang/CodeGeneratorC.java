package com.example.minilang;
import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorC {
    StringBuilder sb = new StringBuilder();

    public String CodeGeneratorC(String name, Ast.Program program) { // name is the name of the source file, but is really unnecessary if only wokring with one file
        //Output LLVM IR boilerplate here


        LabelGenerator blockGenerator = new LabelGenerator();
        List<Ast.Func> functions = program.functions();

        /*Signatures signatures = new Signatures();
        

        List<Ast.Func> functions = program.functions();

        for(Ast.Func func : functions) {
            signatures.addFunction(name, func.name(), func.params(), func.returnType());
        
        }*/

        for (Ast.Func func : functions) {
            
        }

        return "Tempoorary return avoids error";
    }

    private void println(String s) {
    // System.out.println(s); // turn on for debugging
    sb.append(s);
    sb.append('\n');
  }

}
