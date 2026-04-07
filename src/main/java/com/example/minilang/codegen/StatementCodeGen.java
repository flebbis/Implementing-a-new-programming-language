package com.example.minilang.codegen;

import java.util.HashSet;

import com.example.minilang.DebugMetaData;
import com.example.minilang.ast.Ast.SBlock;
import com.example.minilang.ast.Ast.SDecl;
import com.example.minilang.ast.Ast.SDo;
import com.example.minilang.ast.Ast.SExp;
import com.example.minilang.ast.Ast.SIf;
import com.example.minilang.ast.Ast.SInit;
import com.example.minilang.ast.Ast.SReturn;
import com.example.minilang.ast.Ast.SWhile;
import com.example.minilang.ast.Ast.Stmt;
import com.example.minilang.ast.Ast.TArray;
import static com.example.minilang.codegen.RegisterGenerator.generateRegister;

public class StatementCodeGen extends Helper {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private Environment environment;
    private StringBuilder globals;
    private StringBuilder globalStrings;
    private HashSet<String> functionVariables;
    private ExpressionCodeGen expressionCodeGen;
    private final LabelGenerator labelGenerator;
    private DebugMetaData debugMetaData;

    public StatementCodeGen(StringBuilder sb, Environment environment, StringBuilder globals, StringBuilder globalStrings, HashSet<String> functionVariables, DebugMetaData debugMetaData) {
        this.sb = sb;
        this.environment = environment;
        this.globals = globals;
        this.globalStrings = globalStrings;
        this.functionVariables = functionVariables;
        this.labelGenerator = new LabelGenerator();
        this.expressionCodeGen = new ExpressionCodeGen(sb, globals, globalStrings, functionVariables, environment, debugMetaData);
        this.debugMetaData = debugMetaData;
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
        String condLabel = labelGenerator.generateLabel("while_cond");
        String bodyLabel = labelGenerator.generateLabel("while_body");
        String endLabel = labelGenerator.generateLabel("while_end");

        // Jump to condition check
        sb.append("  br label %").append(condLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(whileStmt.pos().line)).append("\n");

        // Condition check
        sb.append(condLabel).append(":\n");
        String condValue = expressionCodeGen.generateExpression(whileStmt.condition()); // Should be a boolean value (i1 in LLVM)
        // Branch based on condition
        sb.append("  br i1 ").append(condValue).append(", label %").append(bodyLabel).append(", label %").append(endLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(whileStmt.pos().line)).append("\n");

        // Loop body
        sb.append(bodyLabel).append(":\n");
        generateStatement(whileStmt.body());

        // After body, jump back to condition
        sb.append("  br label %").append(condLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(whileStmt.pos().line)).append("\n");

        // End of loop
        sb.append(endLabel).append(":\n");
    }

    private void generateDo(SDo doStmt) {
        String condLabel = labelGenerator.generateLabel("do_cond");
        String bodyLabel = labelGenerator.generateLabel("do_body");
        String endLabel = labelGenerator.generateLabel("do_end");

        String dbg = ", !dbg !" + debugMetaData.getLineId(doStmt.pos().line);

        String times = expressionCodeGen.generateExpression(doStmt.times());

        String counterPtr = generateRegister();
        String limitPtr = generateRegister();

        sb.append(counterPtr).append(" = alloca i32").append(dbg).append("\n");
        sb.append(limitPtr).append(" = alloca i32").append(dbg).append("\n");
        sb.append("store i32 0, i32* ").append(counterPtr).append(dbg).append("\n");
        sb.append("store i32 ").append(times).append(", i32* ").append(limitPtr).append(dbg).append("\n");

        sb.append("br label %").append(condLabel).append(dbg).append("\n");
        sb.append(condLabel).append(":\n");

        String currentCounter = generateRegister();
        String currentLimit = generateRegister();
        String cmpReg = generateRegister();

        sb.append(" ").append(currentCounter).append(" = load i32, i32* ").append(counterPtr).append(dbg).append("\n");
        sb.append(" ").append(currentLimit).append(" = load i32, i32* ").append(limitPtr).append(dbg).append("\n");
        sb.append(" ").append(cmpReg).append(" = icmp slt i32 ").append(currentCounter).append(", ").append(currentLimit).append(dbg).append("\n");
        sb.append(" ").append("br i1 ").append(cmpReg).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append(dbg).append("\n");

        sb.append(bodyLabel).append(":\n");
        generateStatement(doStmt.body());

        String old = generateRegister();
        String next = generateRegister();
        sb.append(old).append(" = load i32, i32* ").append(counterPtr).append(dbg).append("\n");
        sb.append(next).append(" = add i32 ").append(old).append(", 1").append(dbg).append("\n");
        sb.append("store i32 ").append(next).append(", i32* ").append(counterPtr).append(dbg).append("\n");
        sb.append("  br label %").append(condLabel).append(dbg).append("\n");

        sb.append(endLabel).append(":\n");
    }

    private void generateIf(SIf ifStmt) {
        String thenLabel = labelGenerator.generateLabel("if_then");
        String elseLabel = labelGenerator.generateLabel("if_else");
        String endLabel = labelGenerator.generateLabel("if_end");

        String condValue = expressionCodeGen.generateExpression(ifStmt.condition()); // Should be a boolean value (i1 in LLVM)

        // Jump to then if true, else jump to else
        sb.append("  br i1 ").append(condValue).append(", label %").append(thenLabel).append(", label %").append(elseLabel)
         .append(", !dbg !").append(debugMetaData.getLineId(ifStmt.pos().line)).append("\n");

        // Then block
        sb.append(thenLabel).append(":\n");
        generateStatement(ifStmt.thenBranch());
        sb.append("  br label %").append(endLabel)
         .append(", !dbg !").append(debugMetaData.getLineId(ifStmt.pos().line)).append("\n");

        // Else block
        sb.append(elseLabel).append(":\n");
        if (ifStmt.elseBranch() != null) {
            generateStatement(ifStmt.elseBranch());
        }
        sb.append("  br label %").append(endLabel)
         .append(", !dbg !").append(debugMetaData.getLineId(ifStmt.pos().line)).append("\n");

        // End of if
        sb.append(endLabel).append(":\n");

    }


    private void generateBlock(SBlock blockStmt) {
        environment.pushNewScope();
        for (Stmt statement : blockStmt.statements()) {
            generateStatement(statement);
        }
        environment.popScope();
    }
    private void generateDecl(SDecl declStmt) {
        String register = generateRegister();

        sb.append(register).append(" = alloca ").append(convertType(declStmt.type()))
        .append(", !dbg !").append(debugMetaData.getLineId(declStmt.pos().line)).append("\n");
        environment.pushToCurrentScope(declStmt.name(), register);
        // }
    }
    private void generateInit(SInit initStmt) {
        if (!environment.existsInCurrentScope(initStmt.name()) && !(initStmt.type() instanceof TArray)) {
            // Variable not declared yet, so we need to allocate space for it

            String register = generateRegister();

            sb.append(" ").append(register).append(" = alloca ").append(convertType(initStmt.type()))
            .append(", !dbg !").append(debugMetaData.getLineId(initStmt.pos().line)).append("\n");
            environment.pushToCurrentScope(initStmt.name(), register);
        }
        
        if (initStmt.type() instanceof TArray) {
            String value = expressionCodeGen.generateExpression(initStmt.value());
            environment.pushToCurrentScope(initStmt.name(), value);
            //sb.append(" store ").append(convertType(initStmt.type())).append(" ").append(value).append(", ").append(convertType(initStmt.type())).append("* %").append(initStmt.name()).append("\n");

        } else {
            String value = expressionCodeGen.generateExpression(initStmt.value());
            String newRegister = environment.lookup(initStmt.name());
            sb.append(" store ").append(convertType(initStmt.type())).append(" ").append(value).append(", ").append(convertType(initStmt.type())).append("* ").append(newRegister)
            .append(", !dbg !").append(debugMetaData.getLineId(initStmt.pos().line)).append("\n");

        }


    }
    private void generateReturn(SReturn returnStmt) {
        if (returnStmt.value() != null) {
            String value = expressionCodeGen.generateExpression(returnStmt.value());
            sb.append("  ret ").append(convertType(returnStmt.value().type())).append(" ").append(value)
            .append(", !dbg !").append(debugMetaData.getLineId(returnStmt.pos().line)).append("\n");
        } else {
            sb.append("  ret void")
            .append(", !dbg !").append(debugMetaData.getLineId(returnStmt.pos().line)).append("\n");
        }
    }
    private void generateExp(SExp expStmt) {
        expressionCodeGen.generateExpression(expStmt.exp());
    }

}