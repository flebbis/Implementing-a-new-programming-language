package com.example.minilang;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExpressionCodeGen {

    private StringBuilder sb;
    private LabelGenerator labelGen;
    private List<Ast.Func> functions;
    private Set<String> parameters;
    private RegisterGenerator registerGenerator;
    
    public ExpressionCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
        this.parameters = Set.of();
        this.registerGenerator = new RegisterGenerator();
    }

    public ExpressionCodeGen(StringBuilder sb, LabelGenerator labelGen, List<Ast.Func> functions, Set<String> parameters, RegisterGenerator registerGenerator) {
        this.sb = sb;
        this.labelGen = labelGen;
        this.functions = functions;
        this.parameters = parameters;
        this.registerGenerator = registerGenerator;
    }
    
    /**
     * Generate code for an expression and return the register holding the result.
     * Returns: literal values as-is (e.g., "5", "10"), or register names with % prefix (e.g., "%1", "%2")
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
        if (exp instanceof Ast.EAss eAss) return codeGenAss(eAss);
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
        return "\"" + eString.value() + "\"";
    }
    
    private String codeGenBool(Ast.EBool eBool) {
        return eBool.value() ? "1" : "0";
    }
    
    // ===== VARIABLES =====
    private String codeGenId(Ast.EId eId) {
        // If it's a parameter, load from the parameter's local copy
        if (isParameter(eId.name())) {
            String llvmType = toLLVMType(eId.type());
            String reg = registerGenerator.nextReg();
            sb.append("  %").append(reg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* %").append(eId.name()).append("_param\n");
            return "%" + reg;
        }
        // If it's a local variable, load it from memory
        else {
            String llvmType = toLLVMType(eId.type());
            String reg = registerGenerator.nextReg();
            
            sb.append("  %").append(reg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* %").append(eId.name()).append("\n");
            return "%" + reg;
        }
    }
    
    // ===== ARITHMETIC OPERATIONS =====
    private String codeGenOpp(Ast.EOpp eOpp) {
        String left = codeGenExp(eOpp.left());
        String right = codeGenExp(eOpp.right());
        String llvmType = toLLVMType(eOpp.type());
        String result = registerGenerator.nextReg();
        
        String op = switch(eOpp.op()) {
            case ADD -> "add";
            case SUB -> "sub";
            case MUL -> "mul";
            case DIV -> "sdiv";
            case MOD -> "srem";
            case NOT -> "xor";
             default -> throw new RuntimeException("Unknown operator: " + eOpp.op());
        };
        
        sb.append("  %").append(result).append(" = ").append(op).append(" ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenPower(Ast.EPower ePower) {
        String base = codeGenExp(ePower.base());
        String exponent = codeGenExp(ePower.exponent());
        
        // Convert to double for pow()
        String baseDouble = registerGenerator.nextReg();
        String expDouble = registerGenerator.nextReg();
        
        sb.append("  %").append(baseDouble).append(" = sitofp i32 ").append(base).append(" to double\n");
        sb.append("  %").append(expDouble).append(" = sitofp i32 ").append(exponent).append(" to double\n");
        
        // Call pow()
        String powResult = registerGenerator.nextReg();
        sb.append("  %").append(powResult).append(" = call double @pow(double %").append(baseDouble).append(", double %").append(expDouble).append(")\n");
        
        // Convert back to int if needed
        String finalResult = registerGenerator.nextReg();
        sb.append("  %").append(finalResult).append(" = fptosi double %").append(powResult).append(" to i32\n");
        
        return "%" + finalResult;
    }
    
    // ===== COMPARISON OPERATIONS =====
    private String codeGenLt(Ast.ECmp eLt) {
        String left = codeGenExp(eLt.left());
        String right = codeGenExp(eLt.right());
        String llvmType = toLLVMType(eLt.left().type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = icmp slt ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenGt(Ast.ECmp eGt) {
        String left = codeGenExp(eGt.left());
        String right = codeGenExp(eGt.right());
        String llvmType = toLLVMType(eGt.left().type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = icmp sgt ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenGe(Ast.ECmp eGe) {
        String left = codeGenExp(eGe.left());
        String right = codeGenExp(eGe.right());
        String llvmType = toLLVMType(eGe.left().type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = icmp sge ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenLe(Ast.ECmp eLe) {
        String left = codeGenExp(eLe.left());
        String right = codeGenExp(eLe.right());
        String llvmType = toLLVMType(eLe.left().type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = icmp sle ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenNe(Ast.ECmp eNe) {
        String left = codeGenExp(eNe.left());
        String right = codeGenExp(eNe.right());
        String llvmType = toLLVMType(eNe.left().type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = icmp ne ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenEq(Ast.ECmp eEq) {
        String left = codeGenExp(eEq.left());
        String right = codeGenExp(eEq.right());
        String llvmType = toLLVMType(eEq.left().type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = icmp eq ").append(llvmType).append(" ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    // ===== LOGICAL OPERATIONS =====
    private String codeGenAnd(Ast.ELogic eAnd) {
        String left = codeGenExp(eAnd.left());
        String right = codeGenExp(eAnd.right());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = and i1 ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenOr(Ast.ELogic eOr) {
        String left = codeGenExp(eOr.left());
        String right = codeGenExp(eOr.right());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = or i1 ").append(left).append(", ").append(right).append("\n");
        return "%" + result;
    }
    
    private String codeGenNot(Ast.ENot eNot) {
        String exp = codeGenExp(eNot.exp());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = xor i1 ").append(exp).append(", 1\n");
        return "%" + result;
    }
    
    // ===== ASSIGNMENT OPERATIONS =====
    private String codeGenAss(Ast.EAss eAss) {
        String valueReg = codeGenExp(eAss.value());
        String llvmType = toLLVMType(eAss.value().type());
        
        // If it's a parameter, store to _param version
        if (isParameter(eAss.name())) {
            sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(eAss.name()).append("_param\n");
        } else {
            sb.append("  store ").append(llvmType).append(" ").append(valueReg).append(", ").append(llvmType).append("* %").append(eAss.name()).append("\n");
        }
        return valueReg;
    }
    
    private String codeGenPlusAss(Ast.EAss ePlusAss) {
        String currentVal = codeGenLoad(ePlusAss.name(), ePlusAss.type());
        String valueReg = codeGenExp(ePlusAss.value());
        String llvmType = toLLVMType(ePlusAss.type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = add ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        
        if (isParameter(ePlusAss.name())) {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(ePlusAss.name()).append("_param\n");
        } else {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(ePlusAss.name()).append("\n");
        }
        return "%" + result;
    }
    
    private String codeGenMinusAss(Ast.EAss eMinusAss) {
        String currentVal = codeGenLoad(eMinusAss.name(), eMinusAss.type());
        String valueReg = codeGenExp(eMinusAss.value());
        String llvmType = toLLVMType(eMinusAss.type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = sub ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        
        if (isParameter(eMinusAss.name())) {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(eMinusAss.name()).append("_param\n");
        } else {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(eMinusAss.name()).append("\n");
        }
        return "%" + result;
    }
    
    private String codeGenDivAss(Ast.EAss eDivAss) {
        String currentVal = codeGenLoad(eDivAss.name(), eDivAss.type());
        String valueReg = codeGenExp(eDivAss.value());
        String llvmType = toLLVMType(eDivAss.type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = sdiv ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        
        if (isParameter(eDivAss.name())) {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(eDivAss.name()).append("_param\n");
        } else {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(eDivAss.name()).append("\n");
        }
        return "%" + result;
    }
    
    private String codeGenMultAss(Ast.EAss eMultAss) {
        String currentVal = codeGenLoad(eMultAss.name(), eMultAss.type());
        String valueReg = codeGenExp(eMultAss.value());
        String llvmType = toLLVMType(eMultAss.type());
        String result = registerGenerator.nextReg();
        
        sb.append("  %").append(result).append(" = mul ").append(llvmType).append(" ").append(currentVal).append(", ").append(valueReg).append("\n");
        
        if (isParameter(eMultAss.name())) {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(eMultAss.name()).append("_param\n");
        } else {
            sb.append("  store ").append(llvmType).append(" %").append(result).append(", ").append(llvmType).append("* %").append(eMultAss.name()).append("\n");
        }
        return "%" + result;
    }
    
    // ===== INCREMENT/DECREMENT =====
    private String codeGenInc(Ast.EUnary eInc) {
        return "TEMPORARY INC CODE AVOIDS ERROR, NOT ACTUALLY USED";
    }
    
    private String codeGenDec(Ast.EUnary eDec) {
        return "TEMPORARY DEC CODE AVOIDS ERROR, NOT ACTUALLY USED";
    }
    
    // ===== FUNCTION CALLS =====
    private String codeGenCall(Ast.ECall eCall) {
        List<String> argRegisters = new ArrayList<>();
        for (Ast.Exp arg : eCall.args()) {
            String argReg = codeGenExp(arg);
            argRegisters.add(argReg);
        }

        String resultReg = registerGenerator.nextReg();
        sb.append("  %").append(resultReg).append(" = call i32 @").append(eCall.name()).append("(");
        for (int i = 0; i < argRegisters.size(); i++) {
            String argVal = argRegisters.get(i);
            sb.append("i32 ").append(argVal);
            if (i < argRegisters.size() - 1) sb.append(", ");
        }
        sb.append(")\n");
        return "%" + resultReg;
    }
    
    // ===== HELPERS =====
    private String codeGenLoad(String varName, Ast.Type type) {
        String llvmType = toLLVMType(type);
        String reg = registerGenerator.nextReg();
        
        // If it's a parameter, load from _param version
        if (isParameter(varName)) {
            sb.append("  %").append(reg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* %").append(varName).append("_param\n");
        } else {
            sb.append("  %").append(reg).append(" = load ").append(llvmType).append(", ").append(llvmType).append("* %").append(varName).append("\n");
        }
        return "%" + reg;
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

    private boolean isParameter(String name) {
        return parameters.contains(name);
    } 
}