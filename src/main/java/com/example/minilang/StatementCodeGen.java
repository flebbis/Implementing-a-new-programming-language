package com.example.minilang;

import java.util.List;

import com.example.minilang.ast.Ast;

public class StatementCodeGen {
    private StringBuilder sb;
    private LabelGenerator labelGen;
    private List<Ast.Func> functions;
    private DebugMetaData debugMetaData;

    public StatementCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions, DebugMetaData debugMetaData) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
        this.debugMetaData = debugMetaData;
    }

    /**
     * Generate code for a statement
     */
    public void codeGenStmt(Ast.Stmt stmt) {
        if (stmt instanceof Ast.SDecl sDecl) {
            codeGenDecl(sDecl);
        } else if (stmt instanceof Ast.SInit sInit) {
            codeGenInit(sInit);
        } else if (stmt instanceof Ast.SReturn sReturn) {
            codeGenReturn(sReturn);
        } else if (stmt instanceof Ast.SExp sExp) {
            codeGenExp(sExp);
        } else if (stmt instanceof Ast.SWhile sWhile) {
            codeGenWhile(sWhile);
        } else if (stmt instanceof Ast.SDo sDo) {
            codeGenDo(sDo);
        } else if (stmt instanceof Ast.SIf sIf) {
            codeGenIf(sIf);
        } else if (stmt instanceof Ast.SBlock sBlock) {
            codeGenBlock(sBlock);
        }
    }

    // ===== VARIABLE DECLARATION (no initialization) =====
    private void codeGenDecl(Ast.SDecl sDecl) {
        // int x;
        String llvmType = toLLVMType(sDecl.type());

        // Allocate space for the variable as backing store %name.addr
        sb.append("  %").append(sDecl.name()).append(".addr = alloca ").append(llvmType)
        .append(", !dbg !").append(debugMetaData.getLineId(sDecl.pos().line)).append("\n");
    }

    public static String toLLVMType(Ast.Type type) {
        return switch (type) {
            case TInt -> "i32";
            case TBool -> "i1";
            case TString -> "i8*";
            case TDouble -> "double";
            case TVoid -> "void";
            case TUnknown -> "i32";
        };
    }

    // ===== VARIABLE INITIALIZATION =====
    private void codeGenInit(Ast.SInit sInit) {
        // int x = 5;
        String llvmType = toLLVMType(sInit.type());

        // Step 1: Allocate space for the variable (backing store)

        sb.append("  %").append(sInit.name()).append(".addr = alloca ").append(llvmType)
        .append(", !dbg !").append(debugMetaData.getLineId(sInit.pos().line)).append("\n");

        // Step 2: Generate code for the initialization expression
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, sInit.pos().line, debugMetaData);
        String valueReg = exprCodeGen.codeGenExp(sInit.value());

        // Step 3: Store the value into the variable backing store
        sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(sInit.name()).append(".addr")
        .append(", !dbg !").append(debugMetaData.getLineId(sInit.pos().line)).append("\n");
    }

    // ===== EXPRESSION STATEMENT =====
    private void codeGenExp(Ast.SExp sExp) {
        // x++; or foo(5); or just an expression on its own
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions,sExp.pos().line,debugMetaData);
        exprCodeGen.codeGenExp(sExp.exp());  // Generate code but ignore result
    }

    // ===== RETURN =====
    private void codeGenReturn(Ast.SReturn sReturn) {
        if (sReturn.value() != null) {
            ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, sReturn.pos().line, debugMetaData);
            String valueReg = exprCodeGen.codeGenExp(sReturn.value());
            String llvmType = toLLVMType(sReturn.value().type());
            sb.append("  ret ").append(llvmType).append(" ").append(valueReg)
            .append(", !dbg !").append(debugMetaData.getLineId(sReturn.pos().line)).append("\n");
        } else {
            sb.append("  ret void").append(", !dbg !").append(debugMetaData.getLineId(sReturn.pos().line)).append("\n");
        }
    }

    // ===== BLOCK =====
    private void codeGenBlock(Ast.SBlock sBlock) {
        // { statement1; statement2; ... }
        for (Ast.Stmt stmt : sBlock.statements()) {
            codeGenStmt(stmt);
        }
    }

    // ===== IF STATEMENT =====
    private void codeGenIf(Ast.SIf sIf) {
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions,sIf.pos().line,debugMetaData);
        String condReg = exprCodeGen.codeGenExp(sIf.condition());

        String thenLabel = labelGen.generateLabel("then");
        String elseLabel = labelGen.generateLabel("else");
        String endLabel  = labelGen.generateLabel("end");

        sb.append("  br i1 ").append(condReg)
                .append(", label %").append(thenLabel)
                .append(", label %").append(elseLabel)
                .append(", !dbg !").append(debugMetaData.getLineId(sIf.pos().line)).append("\n");

        // Then branch
        sb.append(thenLabel).append(":\n");
        codeGenStmt(sIf.thenBranch());
        boolean thenReturned = lastAppendedIsReturn();
        if (!thenReturned) {
        sb.append("  br label %").append(endLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(sIf.pos().line)).append("\n");
        }

        // Else branch
        sb.append(elseLabel).append(":\n");
        if (sIf.elseBranch() != null) {
            codeGenStmt(sIf.elseBranch());
        }
        boolean elseReturned = lastAppendedIsReturn();
        if (!elseReturned) {
        sb.append("  br label %").append(endLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(sIf.pos().line)).append("\n");
        }

        // Only emit end block if at least one branch falls through
        if (!thenReturned || !elseReturned) {
            sb.append(endLabel).append(":\n");
        }
    }


    // ===== WHILE LOOP =====
    private void codeGenWhile(Ast.SWhile sWhile) {
        // while(condition) { body }
        
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, sWhile.pos().line, debugMetaData);
        
        // Step 1: Generate labels
        String condLabel = labelGen.generateLabel("cond");
        String bodyLabel = labelGen.generateLabel("body");
        String endLabel = labelGen.generateLabel("end");

        // Step 2: Jump to condition check
        sb.append("  br label %").append(condLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(sWhile.pos().line)).append("\n");
        

        // Step 3: Condition label
        sb.append(condLabel).append(":\n");
        String condReg = exprCodeGen.codeGenExp(sWhile.condition());
        sb.append("  br i1 ").append(condReg).append(", label %").append(bodyLabel).append(", label %").append(endLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(sWhile.pos().line)).append("\n");

        // Step 4: Body label
        sb.append(bodyLabel).append(":\n");
        codeGenStmt(sWhile.body());
        if (!lastAppendedIsReturn()) {
        sb.append("  br label %").append(condLabel)
        .append(", !dbg !").append(debugMetaData.getLineId(sWhile.pos().line)).append("\n");  // Loop back
        }

        // Step 5: End label
        sb.append(endLabel).append(":\n");
    }

    // ===== DO-WHILE LOOP =====
    private void codeGenDo(Ast.SDo sDo) {
            // do times { body }
            // This should execute the body 'times' number of times
    
            ExpressionCodeGen exprCodegen = new ExpressionCodeGen(sb, labelGen, functions, sDo.pos().line, debugMetaData);
    
            // Step 1: Evaluate the number of iterations
            String timesReg = exprCodegen.codeGenExp(sDo.times());
    
            // Step 2: Create a counter variable
            String counterVar = labelGen.generateLabel("counter");
            sb.append("  %").append(counterVar).append(" = alloca i32")
            .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
            sb.append("  store i32 0, i32* %").append(counterVar)
            .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
    
            // Step 3: Create labels
            String condLabel = labelGen.generateLabel("cond");
            String bodyLabel = labelGen.generateLabel("body");
            String endLabel = labelGen.generateLabel("end");
    
            // Step 4: Jump to condition
            sb.append("  br label %").append(condLabel)
            .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
    
            // Step 5: Condition check (counter < times)
            sb.append(condLabel).append(":\n");
            String counter = nextReg();
            sb.append("  %").append(counter).append(" = load i32, i32* %").append(counterVar)
            .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
            String cond = nextReg();
            sb.append("  %").append(cond).append(" = icmp slt i32 %").append(counter).append(", ").append(timesReg)
            .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
            sb.append("  br i1 %").append(cond).append(", label %").append(bodyLabel).append(", label %").append(endLabel)
            .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
    
            // Step 6: Body
            sb.append(bodyLabel).append(":\n");
            codeGenStmt(sDo.body());
    
            // If body returned, don't emit increment/branch
        if (!lastAppendedIsReturn()) {
            // Step 7: Increment counter
                String incremented = nextReg();
                sb.append("  %").append(incremented).append(" = add i32 %").append(counter).append(", 1")
                .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
                sb.append("  store i32 %").append(incremented).append(", i32* %").append(counterVar)
                .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
    
                // Step 8: Loop back
                sb.append("  br label %").append(condLabel)
                .append(", !dbg !").append(debugMetaData.getLineId(sDo.pos().line)).append("\n");
            }

            // Step 9: End
            sb.append(endLabel).append(":\n");
        }

    // ===== HELPERS =====
    private int regCounter = 0;

    private String nextReg() {
        return String.valueOf(regCounter++);
    }

    // Helper to detect if the last appended significant line is a 'ret' terminator.
    // This is a simple heuristic but sufficient to avoid emitting branches after returns.
    private boolean lastAppendedIsReturn() {
        int i = sb.length() - 1;
        // Skip trailing whitespace
        while (i >= 0 && Character.isWhitespace(sb.charAt(i))) i--;
        if (i < 0) return false;
        int start = sb.lastIndexOf("\n", i);
        int lineStart = (start == -1) ? 0 : start + 1;
        String lastLine = sb.substring(lineStart, i + 1).trim();
        return lastLine.startsWith("ret ");
    }
}

