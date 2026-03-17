package com.example.minilang;
import java.util.List;

import com.example.minilang.ast.Ast;

public class FunctionCodeGen {
    private StringBuilder sb;
    private LabelGenerator labelGenerator;
    private List<Ast.Func> functions;

    public FunctionCodeGen(StringBuilder sb, LabelGenerator labelGenerator, List<Ast.Func> functions) {
        this.sb = sb;
        this.labelGenerator = labelGenerator;
        this.functions = functions;
    }

    public void codeGenFunDef(Ast.Func func){
        sb.append("define ").append(toLLVMType(func.returnType())).append(" @").append(func.name()).append("(");
        for(int i = 0; i < func.params().size(); i++) {
            Ast.Arg arg = func.params().get(i);
            if(i > 0) {
                sb.append(", ");
            }
            sb.append(toLLVMType(arg.type())).append(" %").append(arg.name());
        }
        sb.append(") {\n");

        sb.append("entry:\n");
        sb.append("; src:").append(func.pos().line).append("\n");


        System.out.println("DEBUG func: " + func.name() + " params: " + func.params() + " size: " + (func.params() == null ? "null" : func.params().size()));

        if (func.params() == null || func.params().isEmpty()) {
        // dummy instruction so prologue assembly has an IR entry to consume
            sb.append("  br label %entry_body\n");
            sb.append("entry_body:\n");
        } else {
            for (Ast.Arg arg : func.params()) {
                String llvmType = toLLVMType(arg.type());
                sb.append("  %").append(arg.name()).append("_addr = alloca ").append(llvmType).append("\n");
                sb.append("  store ").append(llvmType).append(" %").append(arg.name())
                .append(", ").append(llvmType).append("* %").append(arg.name()).append("_addr\n");
    }
}

        StatementCodeGen stmtCodeGen = new StatementCodeGen(sb, labelGenerator, functions);
        stmtCodeGen.codeGenStmt(func.body());
        sb.append("}\n");
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


}