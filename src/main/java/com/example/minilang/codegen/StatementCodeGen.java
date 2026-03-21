package com.example.minilang.codegen;

import java.util.HashSet;

import com.example.minilang.ast.Ast.*;

public class StatementCodeGen extends Helper {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private HashSet<String> declaredVariables;
    private StringBuilder globals;
    private HashSet<String> functionVariables;

    public StatementCodeGen(StringBuilder sb, HashSet<String> declaredVariables, StringBuilder globals, HashSet<String> functionVariables) {
        this.sb = sb;
        this.declaredVariables = declaredVariables;
        this.globals = globals;
        this.functionVariables = functionVariables;
    }

    public void generateStatement(Stmt stmt) {
       if (stmt instanceof SDecl declStmt) { generateDecl(declStmt);}
       else if (stmt instanceof SInit initStmt) { generateInit(initStmt);}
       else if (stmt instanceof SReturn returnStmt) { generateReturn(returnStmt);}
       else if (stmt instanceof SExp expStmt) { generateExp(expStmt);}
       else if (stmt instanceof SWhile whileStmt) { generateWhile(whileStmt);}
       else if (stmt instanceof SDo doStmt) { generateDo(doStmt);}
       else if (stmt instanceof SIf ifStmt) { generateIf(ifStmt);}
       else if (stmt instanceof SBlock blockStmt) { generateBlock(blockStmt);}
    }

    private void generateWhile(SWhile whileStmt) {

    }
    private void generateDo(SDo doStmt) {

    }
    private void generateIf(SIf ifStmt) {

    }
    private void generateBlock(SBlock blockStmt) {
            for (Stmt statement : blockStmt.statements()) {
            StatementCodeGen stmtGen = new StatementCodeGen(sb, declaredVariables, globals, functionVariables);
            stmtGen.generateStatement(statement);
        }
    }
    private void generateDecl(SDecl declStmt) {
        
        
        sb.append(" %").append(declStmt.name()).append(" = alloca ").append(convertType(declStmt.type())).append("\n");
        declaredVariables.add(declStmt.name());

    }
    private void generateInit(SInit initStmt) {
        if (!declaredVariables.contains(initStmt.name())){
                sb.append(" %").append(initStmt.name()).append(" = alloca ").append(convertType(initStmt.type())).append("\n");
                declaredVariables.add(initStmt.name());
        }
        if (initStmt.type() instanceof TArray) {
            ExpressionCodeGen expGen = new ExpressionCodeGen(sb, globals,functionVariables , initStmt.name());
            String value = expGen.generateExpression(initStmt.value());
            //sb.append(" store ").append(convertType(initStmt.type())).append(" ").append(value).append(", ").append(convertType(initStmt.type())).append("* %").append(initStmt.name()).append("\n");

        } else {ExpressionCodeGen expGen = new ExpressionCodeGen(sb, globals, functionVariables);
                String value = expGen.generateExpression(initStmt.value());
                sb.append(" store ").append(convertType(initStmt.type())).append(" ").append(value).append(", ").append(convertType(initStmt.type())).append("* %").append(initStmt.name()).append("\n");

        }


    }
    private void generateReturn(SReturn returnStmt) {
        if (returnStmt.value() != null) {
            ExpressionCodeGen expGen = new ExpressionCodeGen(sb, globals, functionVariables);
            String value = expGen.generateExpression(returnStmt.value());
            sb.append("  ret ").append(convertType(returnStmt.value().type())).append(" ").append(value).append("\n");
        } else {
            sb.append("  ret void\n");
        }
    }
    private void generateExp(SExp expStmt) {
        ExpressionCodeGen expGen = new ExpressionCodeGen(sb, globals, functionVariables);
        expGen.generateExpression(expStmt.exp());
    }

}