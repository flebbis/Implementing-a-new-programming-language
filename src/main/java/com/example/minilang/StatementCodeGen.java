package com.example.minilang;

import java.util.List;
import java.util.Set;
import static com.example.minilang.FunctionCodeGen.toLLVMType;

public class StatementCodeGen {
    private StringBuilder sb;
    private LabelGenerator labelGen;
    private List<Ast.Func> functions;
    private Set<String> currentFunctionParameters;
    private RegisterGenerator registerGenerator;
    private Set<String> declaredVariables;
    
    public StatementCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
        this.currentFunctionParameters = Set.of();
        this.registerGenerator = new RegisterGenerator();
        this.declaredVariables = new java.util.HashSet<>();
    }

    public StatementCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions, Set<String> parameters, RegisterGenerator registerGenerator) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
        this.currentFunctionParameters = parameters;
        this.registerGenerator = registerGenerator;
        this.declaredVariables = new java.util.HashSet<>();
    }
    
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
    
    private void codeGenDecl(Ast.SDecl sDecl) {
        if (currentFunctionParameters.contains(sDecl.name()) || declaredVariables.contains(sDecl.name())) {
            return;
        }
        
        String llvmType = toLLVMType(sDecl.type());
        sb.append("  %").append(sDecl.name()).append(" = alloca ").append(llvmType).append("\n");
        declaredVariables.add(sDecl.name());
    }
    
    private void codeGenInit(Ast.SInit sInit) {
        String llvmType = toLLVMType(sInit.type());
        
        if (!currentFunctionParameters.contains(sInit.name()) && !declaredVariables.contains(sInit.name())) {
            sb.append("  %").append(sInit.name()).append(" = alloca ").append(llvmType).append("\n");
            declaredVariables.add(sInit.name());
        }
        
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, currentFunctionParameters, registerGenerator);
        String valueReg = exprCodeGen.codeGenExp(sInit.value());
        
        if (currentFunctionParameters.contains(sInit.name())) {
            sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(sInit.name()).append("_param\n");
        } else {
            sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(sInit.name()).append("\n");
        }
    }
    
    private void codeGenExp(Ast.SExp sExp) {
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, currentFunctionParameters, registerGenerator);
        exprCodeGen.codeGenExp(sExp.exp());
    }
    
    private void codeGenReturn(Ast.SReturn sReturn) {
        if(sReturn.value() != null) {
            ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, currentFunctionParameters, registerGenerator);
            String valueReg = exprCodeGen.codeGenExp(sReturn.value());
            String llvmType = toLLVMType(sReturn.value().type());
            sb.append("  ret ").append(llvmType).append(" ").append(valueReg).append("\n");
        } else {
            sb.append("  ret void\n");
        }
    }
    
    private void codeGenBlock(Ast.SBlock sBlock) {
        for(Ast.Stmt stmt : sBlock.statements()) {
            codeGenStmt(stmt);
        }
    }
    
    private void codeGenIf(Ast.SIf sIf) {
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, currentFunctionParameters, registerGenerator);
        String condReg = exprCodeGen.codeGenExp(sIf.condition());
        
        String thenLabel = labelGen.generateLabel("then");
        String elseLabel = labelGen.generateLabel("else");
        String endLabel = labelGen.generateLabel("end");
        
        sb.append("  br i1 ").append(condReg).append(", label %").append(thenLabel).append(", label %").append(elseLabel).append("\n");
        
        sb.append(thenLabel).append(":\n");
        codeGenStmt(sIf.thenBranch());
        sb.append("  br label %").append(endLabel).append("\n");
        
        sb.append(elseLabel).append(":\n");
        if(sIf.elseBranch() != null) {
            codeGenStmt(sIf.elseBranch());
        }
        sb.append("  br label %").append(endLabel).append("\n");
        
        sb.append(endLabel).append(":\n");
    }
    
    private void codeGenWhile(Ast.SWhile sWhile) {
        ExpressionCodeGen exprCodeGen = new ExpressionCodeGen(sb, labelGen, functions, currentFunctionParameters, registerGenerator);
        
        String condLabel = labelGen.generateLabel("cond");
        String bodyLabel = labelGen.generateLabel("body");
        String endLabel = labelGen.generateLabel("end");
        
        sb.append("  br label %").append(condLabel).append("\n");
        
        sb.append(condLabel).append(":\n");
        String condReg = exprCodeGen.codeGenExp(sWhile.condition());
        sb.append("  br i1 ").append(condReg).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append("\n");
        
        sb.append(bodyLabel).append(":\n");
        codeGenStmt(sWhile.body());
        sb.append("  br label %").append(condLabel).append("\n");
        
        sb.append(endLabel).append(":\n");
    }
    
    private void codeGenDo(Ast.SDo sDo) {
        ExpressionCodeGen exprCodegen = new ExpressionCodeGen(sb, labelGen, functions, currentFunctionParameters, registerGenerator);
        String timesReg = exprCodegen.codeGenExp(sDo.times());
        
        String counterVar = labelGen.generateLabel("counter");
        sb.append("  %").append(counterVar).append(" = alloca i32\n");
        sb.append("  store i32 0, i32* %").append(counterVar).append("\n");
        
        String condLabel = labelGen.generateLabel("cond");
        String bodyLabel = labelGen.generateLabel("body");
        String endLabel = labelGen.generateLabel("end");
        
        sb.append("  br label %").append(condLabel).append("\n");
        
        sb.append(condLabel).append(":\n");
        String counter = registerGenerator.nextReg();
        sb.append("  %").append(counter).append(" = load i32, i32* %").append(counterVar).append("\n");
        String cond = registerGenerator.nextReg();
        sb.append("  %").append(cond).append(" = icmp slt i32 %").append(counter).append(", ").append(timesReg).append("\n");
        sb.append("  br i1 %").append(cond).append(", label %").append(bodyLabel).append(", label %").append(endLabel).append("\n");
        
        sb.append(bodyLabel).append(":\n");
        codeGenStmt(sDo.body());
        
        String incremented = registerGenerator.nextReg();
        sb.append("  %").append(incremented).append(" = add i32 %").append(counter).append(", 1\n");
        sb.append("  store i32 %").append(incremented).append(", i32* %").append(counterVar).append("\n");
        
        sb.append("  br label %").append(condLabel).append("\n");
        
        sb.append(endLabel).append(":\n");
    }
}