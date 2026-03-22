package com.example.minilang.codegen;

import java.util.HashSet;
import static com.example.minilang.codegen.RegisterGenerator.generateRegister;

import com.example.minilang.ast.Ast.*;

public class StatementCodeGen extends Helper {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private HashSet<String> declaredVariables;
    private StringBuilder globals;
    private HashSet<String> functionVariables;
    private ExpressionCodeGen expressionCodeGen;
    private final LabelGenerator labelGenerator;

    public StatementCodeGen(StringBuilder sb, HashSet<String> declaredVariables, StringBuilder globals, HashSet<String> functionVariables) {
        this.sb = sb;
        this.declaredVariables = declaredVariables;
        this.globals = globals;
        this.functionVariables = functionVariables;
        this.labelGenerator = new LabelGenerator();
        this.expressionCodeGen = new ExpressionCodeGen(sb, globals, functionVariables);
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
        sb.append("  br label %").append(condLabel).append("\n");

        // Condition check
        sb.append(condLabel).append(":\n");
        String condValue = expressionCodeGen.generateExpression(whileStmt.condition()); // Should be a boolean value (i1 in LLVM)
        // Branch based on condition
        sb.append("  br i1 ").append(condValue).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append("\n");

        // Loop body
        sb.append(bodyLabel).append(":\n");
        generateStatement(whileStmt.body());

        // After body, jump back to condition
        sb.append("  br label %").append(condLabel).append("\n");

        // End of loop
        sb.append(endLabel).append(":\n");
    }

    private void generateDo(SDo doStmt) {
        String condLabel = labelGenerator.generateLabel("do_cond");
        String bodyLabel = labelGenerator.generateLabel("do_body");
        String endLabel = labelGenerator.generateLabel("do_end");

        String times = expressionCodeGen.generateExpression(doStmt.times()); // Should be an integer value (i32 in LLVM)

        String counterPtr = generateRegister();
        String limitPtr = generateRegister();

        // Initialize counter & limit (gets value of times)
        sb.append(counterPtr).append(" = alloca i32\n");
        sb.append(limitPtr).append(" = alloca i32\n");
        sb.append("store i32 0, i32* ").append(counterPtr).append("\n");
        sb.append("store i32 ").append(times).append(", i32* ").append(limitPtr).append("\n");

        // Condition
        sb.append("br label %").append(condLabel).append("\n");
        sb.append(condLabel).append(":\n");

        String currentCounter = generateRegister();
        String currentLimit = generateRegister();
        String cmpReg = generateRegister();

        sb.append(" ").append(currentCounter).append(" = load i32, i32* ").append(counterPtr).append("\n"); // Load current counter value
        sb.append(" ").append(currentLimit).append(" = load i32, i32* ").append(limitPtr).append("\n"); // Load current counter value
        sb.append(" ").append(cmpReg).append(" = icmp slt i32 ").append(currentCounter).append(", ").append(currentLimit).append("\n"); // Compare counter with limit
        sb.append(" ").append("br i1 ").append(cmpReg).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append("\n"); // jump to body if counter < limit

        // Body
        sb.append(bodyLabel).append(":\n");
        generateStatement(doStmt.body());
        // Increment counter
        String old = generateRegister();
        String next = generateRegister();
        sb.append(old).append(" = load i32, i32* ").append(counterPtr).append("\n");
        sb.append(next).append(" = add i32 ").append(old).append(", 1\n");
        sb.append("store i32 ").append(next).append(", i32* ").append(counterPtr).append("\n");
        // After body, jump back to condition
        sb.append("  br label %").append(condLabel).append("\n");

        // End of loop
        sb.append(endLabel).append(":\n");

    }

    private void generateIf(SIf ifStmt) {
        String thenLabel = labelGenerator.generateLabel("if_then");
        String elseLabel = labelGenerator.generateLabel("if_else");
        String endLabel = labelGenerator.generateLabel("if_end");

        String condValue = expressionCodeGen.generateExpression(ifStmt.condition()); // Should be a boolean value (i1 in LLVM)

        // Jump to then if true, else jump to else
        sb.append("  br i1 ").append(condValue).append(", label %").append(thenLabel).append(", label %").append(elseLabel).append("\n");

        // Then block
        sb.append(thenLabel).append(":\n");
        generateStatement(ifStmt.thenBranch());
        sb.append("  br label %").append(endLabel).append("\n");

        // Else block
        sb.append(elseLabel).append(":\n");
        if (ifStmt.elseBranch() != null) {
            generateStatement(ifStmt.elseBranch());
        }
        sb.append("  br label %").append(endLabel).append("\n");

        // End of if
        sb.append(endLabel).append(":\n");

    }


    private void generateBlock(SBlock blockStmt) {
            for (Stmt statement : blockStmt.statements()) {
            generateStatement(statement);
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

        } else {
            ExpressionCodeGen expGen = new ExpressionCodeGen(sb, globals, functionVariables);
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