package com.example.minilang;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FunctionCodeGen {
    private StringBuilder sb;
    private LabelGenerator labelGenerator;
    private List<Ast.Func> functions;
    private RegisterGenerator registerGenerator;

    public FunctionCodeGen(StringBuilder sb, LabelGenerator labelGenerator, List<Ast.Func> functions) {
        this.sb = sb;
        this.labelGenerator = labelGenerator;
        this.functions = functions;
        this.registerGenerator = new RegisterGenerator();
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

        // ← FOR EACH PARAMETER, ALLOCATE LOCAL STORAGE
        Set<String> paramNames = new HashSet<>();
        for (Ast.Arg arg : func.params()) {
            String llvmType = toLLVMType(arg.type());
            // Create a local variable for the parameter
            sb.append("  %").append(arg.name()).append("_param = alloca ").append(llvmType).append("\n");
            sb.append("  store ").append(llvmType).append(" %").append(arg.name()).append(", ").append(llvmType).append("* %").append(arg.name()).append("_param\n");
            paramNames.add(arg.name());
        }

        // Reset register generator for each function
        registerGenerator.reset();

        StatementCodeGen stmtCodeGen = new StatementCodeGen(sb, labelGenerator, functions, paramNames, registerGenerator);
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