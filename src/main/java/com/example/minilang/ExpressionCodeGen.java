package com.example.minilang;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.List;

public class ExpressionCodeGen {


    private StringBuilder sb;
    private LabelGenerator labelGen;
    private List<Ast.Func> functions;
    // Register names are generated via labelGen to ensure global uniqueness

    public ExpressionCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
    }
    
    /**
     * Generate code for an expression and return the register holding the result
     */
    public String codeGenExp(Ast.Exp exp) {
    if (exp instanceof Ast.EInt eInt) return codeGenInt(eInt);
    if (exp instanceof Ast.EDouble eDouble) return codeGenDouble(eDouble);
    if (exp instanceof Ast.EString eString) return codeGenString(eString);
    if (exp instanceof Ast.EBool eBool) return codeGenBool(eBool);
    if (exp instanceof Ast.EId eId) return codeGenId(eId);
    if (exp instanceof Ast.ENot eNot) return codeGenNot(eNot);
    if (exp instanceof Ast.EOpp eOpp) return codeGenOpp(eOpp);
    if (exp instanceof Ast.ECall eCall) return codeGenCall(eCall);
    if (exp instanceof Ast.EPower ePower) return codeGenPower(ePower);
    if (exp instanceof Ast.ECmp eLt && eLt.op() == Ast.CmpOp.LT) return codeGenLt(eLt);
    if (exp instanceof Ast.ECmp eGt && eGt.op() == Ast.CmpOp.GT) return codeGenGt(eGt);
    if (exp instanceof Ast.ECmp eGe && eGe.op() == Ast.CmpOp.GE) return codeGenGe(eGe);
    if (exp instanceof Ast.ECmp eLe && eLe.op() == Ast.CmpOp.LE) return codeGenLe(eLe);
    if (exp instanceof Ast.ECmp eNe && eNe.op() == Ast.CmpOp.NE) return codeGenNe(eNe);
    if (exp instanceof Ast.ECmp eEq && eEq.op() == Ast.CmpOp.EQ) return codeGenEq(eEq);
    if (exp instanceof Ast.ELogic eAnd && eAnd.op() == Ast.LogicOp.AND) return codeGenAnd(eAnd);
    if (exp instanceof Ast.ELogic eOr && eOr.op() == Ast.LogicOp.OR) return codeGenOr(eOr);
    if (exp instanceof Ast.EAss eAss && eAss.op() == Ast.AssOp.ASSIGN) return codeGenAss(eAss);
    if (exp instanceof Ast.EAss ePlusAss && ePlusAss.op() == Ast.AssOp.PLUS_ASSIGN) return codeGenPlusAss(ePlusAss);
    if (exp instanceof Ast.EAss eMinusAss && eMinusAss.op() == Ast.AssOp.MINUS_ASSIGN) return codeGenMinusAss(eMinusAss);
    if (exp instanceof Ast.EAss eDivAss && eDivAss.op() == Ast.AssOp.DIV_ASSIGN) return codeGenDivAss(eDivAss);
    if (exp instanceof Ast.EAss eMultAss && eMultAss.op() == Ast.AssOp.MULT_ASSIGN) return codeGenMultAss(eMultAss);
    if (exp instanceof Ast.EUnary eInc && eInc.op() == Ast.UnaryOp.INC) return codeGenInc(eInc);
    if (exp instanceof Ast.EUnary eDec && eDec.op() == Ast.UnaryOp.DEC) return codeGenDec(eDec);
    return "0";
    }
    
    // ===== LITERALS =====
    private String codeGenInt(Ast.EInt eInt) {
        return String.valueOf(eInt.value());
    }
    
    private String codeGenDouble(Ast.EDouble eDouble) {
        return String.valueOf(eDouble.value());
    }
    
    private String codeGenString(Ast.EString eString) {
        // Strings are more complex - for now, simplified
        return "\"" + eString.value() + "\"";
    }
    
    private String codeGenBool(Ast.EBool eBool) {
        return eBool.value() ? "1" : "0";
    }
    
    // ===== VARIABLES =====
    private String codeGenId(Ast.EId eId) {
        // Load variable from its backing store: %name.addr
        String llvmType = toLLVMType(eId.type());
        String reg = nextReg();
        sb.append("  ").append(reg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* %").append(eId.name()).append(".addr\n");
        return reg;
    }
    
    // ===== ARITHMETIC OPERATIONS =====
    private String codeGenOpp(Ast.EOpp eOpp) {
        String left = codeGenExp(eOpp.left());
        String right = codeGenExp(eOpp.right());
        String llvmType = toLLVMType(eOpp.type());
        String result = nextReg();

        String op = switch (eOpp.op()) {
            case ADD -> "add";
            case SUB -> "sub";
            case MUL -> "mul";
            case DIV -> "sdiv";  // signed division
            case MOD -> "srem";  // signed remainder
            default -> throw new RuntimeException("Unknown operator: " + eOpp.op());
        };

        sb.append("  ").append(result).append(" = ").append(op).append(" ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
private String codeGenPower(Ast.EPower ePower) {
    String base = codeGenExp(ePower.base());
    String exponent = codeGenExp(ePower.exponent());
    String result = nextReg();
    
    // Convert to double for pow()
    String baseDouble = nextReg();
    String expDouble = nextReg();
    
    sb.append("  ").append(baseDouble).append(" = sitofp i32 ").append(base).append(" to double\n");
    sb.append("  ").append(expDouble).append(" = sitofp i32 ").append(exponent).append(" to double\n");
    
    // Call pow()
    String powResult = nextReg();
    sb.append("  ").append(powResult).append(" = call double @pow(double ").append(baseDouble).append(", double ").append(expDouble).append(")\n");
    
    // Convert back to int if needed
    String finalResult = nextReg();
    sb.append("  ").append(finalResult).append(" = fptosi double ").append(powResult).append(" to i32\n");
    
    return finalResult;
}
    
    // ===== COMPARISON OPERATIONS =====
    private String codeGenLt(Ast.ECmp eLt) {
        String left = codeGenExp(eLt.left());
        String right = codeGenExp(eLt.right());
        String llvmType = toLLVMType(eLt.left().type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = icmp slt ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenGt(Ast.ECmp eGt) {
        String left = codeGenExp(eGt.left());
        String right = codeGenExp(eGt.right());
        String llvmType = toLLVMType(eGt.left().type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = icmp sgt ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenGe(Ast.ECmp eGe) {
        String left = codeGenExp(eGe.left());
        String right = codeGenExp(eGe.right());
        String llvmType = toLLVMType(eGe.left().type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = icmp sge ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenLe(Ast.ECmp eLe) {
        String left = codeGenExp(eLe.left());
        String right = codeGenExp(eLe.right());
        String llvmType = toLLVMType(eLe.left().type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = icmp sle ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenNe(Ast.ECmp eNe) {
        String left = codeGenExp(eNe.left());
        String right = codeGenExp(eNe.right());
        String llvmType = toLLVMType(eNe.left().type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = icmp ne ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenEq(Ast.ECmp eEq) {
        String left = codeGenExp(eEq.left());
        String right = codeGenExp(eEq.right());
        String llvmType = toLLVMType(eEq.left().type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = icmp eq ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    // ===== LOGICAL OPERATIONS =====
    private String codeGenAnd(Ast.ELogic eAnd) {
        String left = codeGenExp(eAnd.left());
        String right = codeGenExp(eAnd.right());
        String result = nextReg();
        sb.append("  ").append(result).append(" = and i1 ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenOr(Ast.ELogic eOr) {
        String left = codeGenExp(eOr.left());
        String right = codeGenExp(eOr.right());
        String result = nextReg();
        sb.append("  ").append(result).append(" = or i1 ").append(left).append(", ").append(right).append("\n");
        return result;
    }
    
    private String codeGenNot(Ast.ENot eNot) {
        String exp = codeGenExp(eNot.exp());
        String result = nextReg();
        sb.append("  ").append(result).append(" = xor i1 ").append(exp).append(", 1\n");
        return result;
    }
    
    // ===== ASSIGNMENT OPERATIONS =====
    private String codeGenAss(Ast.EAss eAss) {
        // x = value -> store into x.addr
        String valueReg = codeGenExp(eAss.value());
        String llvmType = toLLVMType(eAss.value().type());
        sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(eAss.name()).append(".addr\n");
        return valueReg;  // Return the assigned value
    }
    
    private String codeGenPlusAss(Ast.EAss ePlusAss) {
        // x += value → x = x + value
        String currentVal = codeGenLoad(ePlusAss.name(), ePlusAss.type());
        String valueReg = codeGenExp(ePlusAss.value());
        String llvmType = toLLVMType(ePlusAss.type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = add ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        sb.append("  store ").append(llvmType).append(" ").append(result).append(", ").append(llvmType).append("* %").append(ePlusAss.name()).append(".addr\n");
        return result;
    }
    
    private String codeGenMinusAss(Ast.EAss eMinusAss) {
        // x -= value → x = x - value
        String currentVal = codeGenLoad(eMinusAss.name(), eMinusAss.type());
        String valueReg = codeGenExp(eMinusAss.value());
        String llvmType = toLLVMType(eMinusAss.type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = sub ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        sb.append("  store ").append(llvmType).append(" ").append(result).append(", ").append(llvmType).append("* %").append(eMinusAss.name()).append(".addr\n");
        return result;
    }
    
    private String codeGenDivAss(Ast.EAss eDivAss) {
        // x /= value → x = x / value
        String currentVal = codeGenLoad(eDivAss.name(), eDivAss.type());
        String valueReg = codeGenExp(eDivAss.value());
        String llvmType = toLLVMType(eDivAss.type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = sdiv ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        sb.append("  store ").append(llvmType).append(" ").append(result).append(", ").append(llvmType).append("* %").append(eDivAss.name()).append(".addr\n");
        return result;
    }
    
    private String codeGenMultAss(Ast.EAss eMultAss) {
        // x *= value → x = x * value
        String currentVal = codeGenLoad(eMultAss.name(), eMultAss.type());
        String valueReg = codeGenExp(eMultAss.value());
        String llvmType = toLLVMType(eMultAss.type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = mul ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        sb.append("  store ").append(llvmType).append(" ").append(result).append(", ").append(llvmType).append("* %").append(eMultAss.name()).append(".addr\n");
        return result;
    }
    
    // ===== INCREMENT/DECREMENT =====
    private String codeGenInc(Ast.EUnary eInc) {
        // x++ → x = x + 1
       /* String currentVal = codeGenLoad(eInc.name(), eInc.type());
        String llvmType = toLLVMType(eInc.type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = add ").append(llvmType).append(" ").append(currentVal).append(", 1\n");
        sb.append("  store ").append(llvmType).append(" ").append(result).append(", ").append(llvmType).append("* %").append(eInc.name()).append("\n");
        return currentVal;  // Post-increment returns old value*/
        return "TEMPORARY INC CODE AVOIDS ERROR, NOT ACTUALLY USED";
    }
    
    private String codeGenDec(Ast.EUnary eDec) {
       /*  // x-- → x = x - 1
        String currentVal = codeGenLoad(eDec.name(), eDec.type());
        String llvmType = toLLVMType(eDec.type());
        String result = nextReg();
        sb.append("  ").append(result).append(" = sub ").append(llvmType).append(" ").append(currentVal).append(", 1\n");
        sb.append("  store ").append(llvmType).append(" ").append(result).append(", ").append(llvmType).append("* %").append(eDec.name()).append("\n");
        return currentVal;  // Post-decrement returns old value*/
        return "TEMPORARY DEC CODE AVOIDS ERROR, NOT ACTUALLY USED";
    }
    
    // ===== FUNCTION CALLS =====
    private String codeGenCall(Ast.ECall eCall) {
        // Handle built-in print function
        if (eCall.name().equals("print")) {
            return codeGenPrint(eCall);
        }

        List<String> argRegisters = new ArrayList<>();
        List<String> argTypes = new ArrayList<>();
        for (Ast.Exp arg : eCall.args()) {
            String argReg = codeGenExp(arg);  // This appends load instructions to sb
            argRegisters.add(argReg);         // Store the resulting registers
            argTypes.add(toLLVMType(arg.type()));
        }

        // Try to find the function definition to know its return type
        Ast.Func targetFunc = null;
        if (functions != null) {
            for (Ast.Func f : functions) {
                if (f.name().equals(eCall.name())) {
                    targetFunc = f;
                    break;
                }
            }
        }

        String retType = (targetFunc != null) ? toLLVMType(targetFunc.returnType()) : "i32";

        // Build parameter list with proper types
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < argRegisters.size(); i++) {
            if (i > 0) params.append(", ");
            params.append(argTypes.get(i)).append(" ").append(argRegisters.get(i));
        }

        // Emit call: if void, no assignment; otherwise assign to a typed register
        if ("void".equals(retType)) {
            sb.append("  call void @").append(eCall.name()).append("(").append(params).append(")\n");
            return ""; // void returns no value
        } else {
            String resultReg = nextReg();
            sb.append("  ").append(resultReg).append(" = call ").append(retType).append(" @").append(eCall.name()).append("(").append(params).append(")\n");
            return resultReg;
        }
    }

    // ===== BUILT-IN PRINT =====
    private String codeGenPrint(Ast.ECall eCall) {
        // print(value) → call printf with appropriate format string
        // Supports: int (%d), double (%f), string (%s), bool (%d as 0/1)
        if (eCall.args().isEmpty()) {
            // print() with no args → just print a newline
            String fmtPtr = nextReg();
            sb.append("  ").append(fmtPtr)
              .append(" = getelementptr [2 x i8], [2 x i8]* @.fmt.newline, i32 0, i32 0\n");
            String resultReg = nextReg();
            sb.append("  ").append(resultReg)
              .append(" = call i32 (i8*, ...) @printf(i8* ").append(fmtPtr).append(")\n");
            return resultReg;
        }

        Ast.Exp arg = eCall.args().get(0);
        String argReg = codeGenExp(arg);
        Ast.Type argType = arg.type();

        String fmtGlobal;
        String fmtSize;

        switch (argType) {
            case TInt -> {
                fmtGlobal = "@.fmt.int";
                fmtSize = "[4 x i8]";
            }
            case TDouble -> {
                fmtGlobal = "@.fmt.double";
                fmtSize = "[4 x i8]";
            }
            case TString -> {
                fmtGlobal = "@.fmt.string";
                fmtSize = "[4 x i8]";
            }
            case TBool -> {
                fmtGlobal = "@.fmt.int";   // bools print as 0/1
                fmtSize = "[4 x i8]";
                // Extend i1 to i32 for printf
                String extended = nextReg();
                sb.append("  ").append(extended)
                  .append(" = zext i1 ").append(argReg).append(" to i32\n");
                argReg = extended;
            }
            default -> {
                fmtGlobal = "@.fmt.int";
                fmtSize = "[4 x i8]";
            }
        }

        // Get pointer to format string
        String fmtPtr = nextReg();
        sb.append("  ").append(fmtPtr)
          .append(" = getelementptr ").append(fmtSize).append(", ").append(fmtSize).append("* ")
          .append(fmtGlobal).append(", i32 0, i32 0\n");

        // Call printf
        String llvmArgType = (argType == Ast.Type.TBool) ? "i32" : toLLVMType(argType);
        String resultReg = nextReg();
        sb.append("  ").append(resultReg)
          .append(" = call i32 (i8*, ...) @printf(i8* ").append(fmtPtr)
          .append(", ").append(llvmArgType).append(" ").append(argReg).append(")\n");
        return resultReg;
    }

    // ===== HELPERS =====
    private String codeGenLoad(String varName, Ast.Type type) {
        String llvmType = toLLVMType(type);
        String reg = nextReg();
        sb.append("  ").append(reg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* %").append(varName).append(".addr\n");
        return reg;
    }
    
    private String nextReg() {
        return "%" + labelGen.generateLabel("r");
    }
    
    private String toLLVMType(Ast.Type type) {
        return switch(type) {
            case TInt -> "i32";
            case TDouble -> "double";
            case TString -> "i8*";
            case TBool -> "i1";
            case TVoid -> "void";
            case TUnknown -> "i32";
        };
    }

}

