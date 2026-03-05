package com.example.minilang;

import java.util.List;
import static com.example.minilang.FunctionCodeGen.toLLVMType;

public class StatementCodeGen {
    private StringBuilder sb;
    private LabelGenerator labelGen;
    private List<Ast.Func> functions;
    
    public StatementCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
    }
    
    /**
     * Generate code for a statement
     */
    public void codeGenStmt(Ast.Stmt stmt) {
        if(stmt instanceof Ast.SDecl sDecl) {
            codeGenDecl(sDecl);
        } else if(stmt instanceof Ast.SInit sInit) {
            codeGenInit(sInit);
        } else if(stmt instanceof Ast.SReturn sReturn) {
            codeGenReturn(sReturn);
        } else if(stmt instanceof Ast.SExp sExp) {
            codeGenExp(sExp);
        } else if(stmt instanceof Ast.SWhile sWhile) {
            codeGenWhile(sWhile);
        } else if(stmt instanceof Ast.SDo sDo) {
            codeGenDo(sDo);
        } else if(stmt instanceof Ast.SIf sIf) {
            codeGenIf(sIf);
        } else if(stmt instanceof Ast.SBlock sBlock) {
            codeGenBlock(sBlock);
        }
    }
    
    // ===== VARIABLE DECLARATION (no initialization) =====
    private void codeGenDecl(Ast.SDecl sDecl) {
        // int x;
        String llvmType = toLLVMType(sDecl.type());
        
        // Allocate space for the variable
        sb.append("  %").append(sDecl.name()).append(" = alloca ").append(llvmType).append("\n");
    }
    
    // ===== VARIABLE INITIALIZATION =====
    private void codeGenInit(Ast.SInit sInit) {
        // int x = 5;
        String llvmType = toLLVMType(sInit.type());
        
        // Step 1: Allocate space for the variable
        sb.append("  %").append(sInit.name()).append(" = alloca ").append(llvmType).append("\n");
        
        // Step 2: Generate code for the initialization expression
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions);
        String valueReg = exprCodeGen.codeGenExp(sInit.value());
        
        // Step 3: Store the value into the variable
        sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(sInit.name()).append("\n");
    }
    
    // ===== EXPRESSION STATEMENT =====
    private void codeGenExp(Ast.SExp sExp) {
        // x++; or foo(5); or just an expression on its own
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions);
        exprCodeGen.codeGenExp(sExp.exp());  // Generate code but ignore result
    }
    
    // ===== RETURN =====
    private void codeGenReturn(Ast.SReturn sReturn) {
        System.out.println("DEBUG: codeGenReturn called");
        System.out.println("DEBUG: sReturn.value() == null? " + (sReturn.value() == null));
        // return x;
        if(sReturn.value() != null) {
            ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions);
            String valueReg = exprCodeGen.codeGenExp(sReturn.value());
            String llvmType = toLLVMType(sReturn.value().type());
            sb.append("  ret ").append(llvmType).append(" ").append(valueReg).append("\n");
        } else {
            sb.append("  ret void\n");
        }
    }
    
    // ===== BLOCK =====
    private void codeGenBlock(Ast.SBlock sBlock) {
        // { statement1; statement2; ... }
        for(Ast.Stmt stmt : sBlock.statements()) {
            codeGenStmt(stmt);
        }
    }
    
    // ===== IF STATEMENT =====
    private void codeGenIf(Ast.SIf sIf) {
        // if(condition) { thenBranch } else { elseBranch }
        
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions);
        
        // Step 1: Generate code for condition
        String condReg = exprCodeGen.codeGenExp(sIf.condition());
        
        // Step 2: Generate labels for branches
        String thenLabel = labelGen.generateLabel("then");
        String elseLabel = labelGen.generateLabel("else");
        String endLabel = labelGen.generateLabel("end");
        
        // Step 3: Branch on condition
        sb.append("  br i1 ").append(condReg).append(", label %").append(thenLabel).append(", label %").append(elseLabel).append("\n");
        
        // Step 4: Then branch
        sb.append(thenLabel).append(":\n");
        codeGenStmt(sIf.thenBranch());
        sb.append("  br label %").append(endLabel).append("\n");
        
        // Step 5: Else branch
        sb.append(elseLabel).append(":\n");
        if(sIf.elseBranch() != null) {
            codeGenStmt(sIf.elseBranch());
        }
        sb.append("  br label %").append(endLabel).append("\n");
        
        // Step 6: End label
        sb.append(endLabel).append(":\n");
    }
    
    // ===== WHILE LOOP =====
    private void codeGenWhile(Ast.SWhile sWhile) {
        // while(condition) { body }
        
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions);
        
        // Step 1: Generate labels
        String condLabel = labelGen.generateLabel("cond");
        String bodyLabel = labelGen.generateLabel("body");
        String endLabel = labelGen.generateLabel("end");
        
        // Step 2: Jump to condition check
        sb.append("  br label %").append(condLabel).append("\n");
        
        // Step 3: Condition label
        sb.append(condLabel).append(":\n");
        String condReg = exprCodeGen.codeGenExp(sWhile.condition());
        sb.append("  br i1 ").append(condReg).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append("\n");
        
        // Step 4: Body label
        sb.append(bodyLabel).append(":\n");
        codeGenStmt(sWhile.body());
        sb.append("  br label %").append(condLabel).append("\n");  // Loop back
        
        // Step 5: End label
        sb.append(endLabel).append(":\n");
    }
    
    // ===== DO-WHILE LOOP =====
    private void codeGenDo(Ast.SDo sDo) {
    // do times { body }
    // This should execute the body 'times' number of times
    
    ExpressionCodeGen exprCodegen = new ExpressionCodeGen(sb, labelGen, functions);
    
    // Step 1: Evaluate the number of iterations
    String timesReg = exprCodegen.codeGenExp(sDo.times());
    
    // Step 2: Create a counter variable
    String counterVar = labelGen.generateLabel("counter");
    sb.append("  %").append(counterVar).append(" = alloca i32\n");
    sb.append("  store i32 0, i32* %").append(counterVar).append("\n");
    
    // Step 3: Create labels
    String condLabel = labelGen.generateLabel("cond");
    String bodyLabel = labelGen.generateLabel("body");
    String endLabel = labelGen.generateLabel("end");
    
    // Step 4: Jump to condition
    sb.append("  br label %").append(condLabel).append("\n");
    
    // Step 5: Condition check (counter < times)
    sb.append(condLabel).append(":\n");
    String counter = nextReg();
    sb.append("  %").append(counter).append(" = load i32, i32* %").append(counterVar).append("\n");
    String cond = nextReg();
    sb.append("  %").append(cond).append(" = icmp slt i32 %").append(counter).append(", ").append(timesReg).append("\n");
    sb.append("  br i1 %").append(cond).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append("\n");
    
    // Step 6: Body
    sb.append(bodyLabel).append(":\n");
    codeGenStmt(sDo.body());
    
    // Step 7: Increment counter
    String incremented = nextReg();
    sb.append("  %").append(incremented).append(" = add i32 %").append(counter).append(", 1\n");
    sb.append("  store i32 %").append(incremented).append(", i32* %").append(counterVar).append("\n");
    
    // Step 8: Loop back
    sb.append("  br label %").append(condLabel).append("\n");
    
    // Step 9: End
    sb.append(endLabel).append(":\n");
}

private int regCounter = 0;
private String nextReg() {
    return String.valueOf(regCounter++);
}
}