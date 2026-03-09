package com.example.minilang;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class CodeGenerator {
    StringBuilder sb = new StringBuilder();

    public String CodeGeneratorC(String name, Ast.Program program) {
        ExpressionCodeGen.resetGlobals();
        
        LabelGenerator blockGenerator = new LabelGenerator();
        List<Ast.Func> functions = program.functions();
        
        // Collect all strings from the AST first
        Set<String> strings = new HashSet<>();
        for (Ast.Func func : functions) {
            collectStrings(func.body(), strings);
        }
        
        FunctionCodeGen functionCodeGen = new FunctionCodeGen(new StringBuilder(), blockGenerator, functions);

        for (Ast.Func func : functions) {
            functionCodeGen.codeGenFunDef(func);
        }

        StringBuilder functionsCode = functionCodeGen.getSb();
        
        println("declare i32 @printf(i8*, ...)");
        println("declare double @pow(double, double)");
        println("@.fmt.int = private constant [4 x i8] c\"%d\\0A\\00\"");
        println("@.fmt.double = private constant [4 x i8] c\"%f\\0A\\00\"");
        println("@.fmt.string = private constant [4 x i8] c\"%s\\0A\\00\"");
        println("@.fmt.newline = private constant [2 x i8] c\"\\0A\\00\"");
        
        // Add all collected strings
        int counter = 0;
        for (String str : strings) {
            ExpressionCodeGen.registerStringGlobal(str, counter);
            int len = str.length() + 1;
            String escaped = escapeString(str);
            println("@.str." + counter + " = private constant [" + len + " x i8] c\"" + escaped + "\\00\"");
            counter++;
        }
        
        sb.append(functionsCode);

        return sb.toString();
    }
    
    // Recursively collect all string literals from statements
    private void collectStrings(Ast.Stmt stmt, Set<String> strings) {
        if (stmt instanceof Ast.SDecl) {
            // No strings in declarations
        } else if (stmt instanceof Ast.SInit sInit) {
            collectStringsFromExp(sInit.value(), strings);
        } else if (stmt instanceof Ast.SReturn sReturn) {
            if (sReturn.value() != null) {
                collectStringsFromExp(sReturn.value(), strings);
            }
        } else if (stmt instanceof Ast.SExp sExp) {
            collectStringsFromExp(sExp.exp(), strings);
        } else if (stmt instanceof Ast.SIf sIf) {
            collectStringsFromExp(sIf.condition(), strings);
            collectStrings(sIf.thenBranch(), strings);
            if (sIf.elseBranch() != null) {
                collectStrings(sIf.elseBranch(), strings);
            }
        } else if (stmt instanceof Ast.SWhile sWhile) {
            collectStringsFromExp(sWhile.condition(), strings);
            collectStrings(sWhile.body(), strings);
        } else if (stmt instanceof Ast.SDo sDo) {
            collectStringsFromExp(sDo.times(), strings);
            collectStrings(sDo.body(), strings);
        } else if (stmt instanceof Ast.SBlock sBlock) {
            for (Ast.Stmt s : sBlock.statements()) {
                collectStrings(s, strings);
            }
        }
    }
    
    // Recursively collect strings from expressions
    private void collectStringsFromExp(Ast.Exp exp, Set<String> strings) {
        if (exp instanceof Ast.EString eString) {
            strings.add(eString.value());
        } else if (exp instanceof Ast.EInt || exp instanceof Ast.EDouble || exp instanceof Ast.EBool || exp instanceof Ast.EId) {
            // No strings in these
        } else if (exp instanceof Ast.EOpp eOpp) {
            collectStringsFromExp(eOpp.left(), strings);
            collectStringsFromExp(eOpp.right(), strings);
        } else if (exp instanceof Ast.EPower ePower) {
            collectStringsFromExp(ePower.base(), strings);
            collectStringsFromExp(ePower.exponent(), strings);
        } else if (exp instanceof Ast.ECmp eCmp) {
            collectStringsFromExp(eCmp.left(), strings);
            collectStringsFromExp(eCmp.right(), strings);
        } else if (exp instanceof Ast.ELogic eLogic) {
            collectStringsFromExp(eLogic.left(), strings);
            collectStringsFromExp(eLogic.right(), strings);
        } else if (exp instanceof Ast.ENot eNot) {
            collectStringsFromExp(eNot.exp(), strings);
        } else if (exp instanceof Ast.ECall eCall) {
            for (Ast.Exp arg : eCall.args()) {
                collectStringsFromExp(arg, strings);
            }
        } else if (exp instanceof Ast.EAss eAss) {
            collectStringsFromExp(eAss.value(), strings);
        } else if (exp instanceof Ast.EUnary eUnary) {
            collectStringsFromExp(eUnary.exp(), strings);
        }
    }
    
    // Escape string for LLVM
    private String escapeString(String str) {
        StringBuilder escaped = new StringBuilder();
        for (char c : str.toCharArray()) {
            switch (c) {
                case '\n' -> escaped.append("\\0A");
                case '\t' -> escaped.append("\\09");
                case '\r' -> escaped.append("\\0D");
                case '"' -> escaped.append("\\22");
                case '\\' -> escaped.append("\\5C");
                default -> escaped.append(c);
            }
        }
        return escaped.toString();
    }

    private void println(String s) {
        sb.append(s);
        sb.append('\n');
    }
}