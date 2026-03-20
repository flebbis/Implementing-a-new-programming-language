package com.example.minilang.codegen;

import com.example.minilang.ast.Ast.*;
import static com.example.minilang.codegen.RegisterGenerator.generateRegister;
public class ExpressionCodeGen extends Helper {

    private StringBuilder sb;
    private StringBuilder globals;
    private String arrayName; // For array initialization, we need to keep track of the array name to generate the correct GEP instructions.
    private int stringCounter = 0;
    public ExpressionCodeGen(StringBuilder sb, StringBuilder globals) {
        this.sb = sb;
        this.globals = globals;
    }

    public ExpressionCodeGen(StringBuilder sb, StringBuilder globals, String arrayName){
        this.sb = sb;
        this.globals = globals;
        this.arrayName = arrayName;
    }

    public String generateExpression(Exp exp) {
            if (exp instanceof EInt intExp) return generateInt(intExp);
            if (exp instanceof EDouble doubleExp) return generateDouble(doubleExp);
            if (exp instanceof EString stringExp) return generateString(stringExp);
            if (exp instanceof EBool boolExp) return generateBool(boolExp);
            if (exp instanceof EId idExp) return generateId(idExp);
            if (exp instanceof ECall callExp) return generateCall(callExp);
            if (exp instanceof ENot notExp) return generateNot(notExp);
            if (exp instanceof EPower powerExp) return generatePower(powerExp);
            if (exp instanceof EOpp oppExp) return generateOpp(oppExp);
            if (exp instanceof ECmp cmpExp) return generateCmp(cmpExp);
            if (exp instanceof ELogic logicExp) return generateLogic(logicExp);
            if (exp instanceof EAss assExp) return generateAss(assExp);
            if (exp instanceof EArray arrayExp) return generateArray(arrayExp);
            if (exp instanceof EArrayIndex arrayIndexExp) return generateArrayIndex(arrayIndexExp);
            if (exp instanceof EUnary unaryExp) return generateUnary(unaryExp);
            return "0";
    }

    private String generateInt(EInt intExp) {
        return String.valueOf(intExp.value());
    }
    private String generateDouble(EDouble doubleExp) {
        return String.valueOf(doubleExp.value());
    }
    private String generateString(EString stringExp) {
        String value = stringExp.value();
        value = value.substring(1, value.length() - 1);
        int length = value.length() + 1; // +1 for null terminator
        String globalName = "@.str." + stringCounter++;
        String register = generateRegister();
        globals.append(globalName).append(" = private constant [").append(length).append(" x i8] c\"").append(value).append("\\00\"\n");
        sb.append(register).append(" = getelementptr inbounds [").append(length).append(" x i8], [").append(length).append(" x i8]* ").append(globalName).append(", i32 0, i32 0\n");
        return register;
    }
    private String generateBool(EBool boolExp) {
        return String.valueOf(boolExp.value());
    }
    private String generateId(EId idExp) {
        String register = generateRegister();
        sb.append(register).append(" = load ").append(convertType(idExp.type())).append(", ").append(convertType(idExp.type())).append("* %").append(idExp.name()).append("\n");
        return register;

    }
    private String generateCall(ECall callExp) {
        return "temp";

    }
    private String generateNot(ENot notExp) {
        String value = generateExpression(notExp.exp());
        String register = generateRegister();
        sb.append(register).append(" = xor i1 ").append(value).append(", 1\n");
        return register;

    }
    private String generatePower(EPower powerExp) {
        String base = generateExpression(powerExp.base());
        String exponent = generateExpression(powerExp.exponent());
        String register = generateRegister();
        sb.append(register).append(" = call ").append(convertType(powerExp.type())).append(" @pow(").append(convertType(powerExp.base().type())).append(" ").append(base).append(", ").append(convertType(powerExp.exponent().type())).append(" ").append(exponent).append(")\n");
        return register;

    }
    private String generateOpp(EOpp oppExp) {
        String left = generateExpression(oppExp.left());
        String right = generateExpression(oppExp.right());
        String register = generateRegister();
        switch (oppExp.op()) {
            case ADD -> {sb.append(register).append(" = add ").append(convertType(oppExp.type())).append(" ").append(left).append(", ").append(right).append("\n");} 
            case SUB -> {sb.append(register).append(" = sub ").append(convertType(oppExp.type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case MUL -> {sb.append(register).append(" = mul ").append(convertType(oppExp.type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case DIV -> {sb.append(register).append(" = sdiv ").append(convertType(oppExp.type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case MOD -> {sb.append(register).append(" = srem ").append(convertType(oppExp.type())).append(" ").append(left).append(", ").append(right).append("\n");}
        }
        return register;

    }
    private String generateCmp(ECmp cmpExp) {
        String left = generateExpression(cmpExp.left());
        String right = generateExpression(cmpExp.right());
        String register = generateRegister();
        switch (cmpExp.op()) {
            case LT -> {sb.append(register).append(" = icmp slt ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case GT -> {sb.append(register).append(" = icmp sgt ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case LE -> {sb.append(register).append(" = icmp sle ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case GE -> {sb.append(register).append(" = icmp sge ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case EQ -> {sb.append(register).append(" = icmp eq ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case NE -> {sb.append(register).append(" = icmp ne ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
        }
        return register;

    }
    private String generateLogic(ELogic logicExp) {
        String left = generateExpression(logicExp.left());
        String right = generateExpression(logicExp.right());
        String register = generateRegister();
        switch (logicExp.op()) {
            case AND -> {sb.append(register).append(" = and ").append(convertType(logicExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case OR -> {sb.append(register).append(" = or ").append(convertType(logicExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}    
        }
        return register;

    }
    private String generateAss(EAss assExp) {
        String value = generateExpression(assExp.value());
        if (assExp.op() == AssOp.ASSIGN) {
        sb.append("store ").append(convertType(assExp.value().type())).append(" ").append(value).append(", ").append(convertType(assExp.value().type())).append("* %").append(assExp.name()).append("\n");
        return value; }
            
        String operation = switch (assExp.op()) {
            case PLUS_ASSIGN -> "add";
            case MINUS_ASSIGN -> "sub";
            case MULT_ASSIGN -> "mul";
            case DIV_ASSIGN -> "sdiv";
            default -> "default assignment value";
        };
        String register = generateRegister();
        sb.append(register).append(" = load ").append(convertType(assExp.value().type())).append(", ").append(convertType(assExp.value().type())).append("* %").append(assExp.name()).append("\n");
        String returnRegister = generateRegister();
        sb.append(returnRegister).append(" = ").append(operation).append(" ").append(convertType(assExp.value().type())).append(" ").append(register).append(", ").append(value).append("\n");
        sb.append("store ").append(convertType(assExp.value().type())).append(" ").append(returnRegister).append(", ").append(convertType(assExp.value().type())).append("* %").append(assExp.name()).append("\n");
        return returnRegister;    
    }   
        
    
    private String generateArray(EArray arrayExp) {
        int numElements = arrayExp.elements().size();
        Type arrayType = arrayExp.type();

        for (int i = 0; i < numElements; i++) {
        Exp elementExp = arrayExp.elements().get(i);
        String value = generateExpression(elementExp);
        String register = generateRegister();
        String elementType = convertType(elementExp.type());
        sb.append(register).append(" = getelementptr inbounds ").append(convertType(arrayType)).append(", ").append(convertType(arrayType)).append(" * %").append(arrayName).append(", ").append(elementType).append(" 0, ").append(elementType).append(" ").append(i).append("\n");
        sb.append("store ").append(elementType).append(" ").append(value).append(", ").append(elementType).append("* ").append(register).append("\n");
        }

        return "%" + arrayName;
    }


    private String generateArrayIndex(EArrayIndex arrayIndexExp) {
        String arrayName = arrayIndexExp.array() instanceof EId id ? id.name() : "unknown";
        String register = generateRegister();
        String index = generateExpression(arrayIndexExp.index());
        String arrayType = convertType(arrayIndexExp.array().type());
        String elementType = convertType(((TArray) arrayIndexExp.array().type()).elementType());
        sb.append(register).append(" = getelementptr inbounds ").append(arrayType).append(", ").append(arrayType).append(" * %").append(arrayName).append(", ").append(elementType).append(" 0, ").append(elementType).append(" ").append(index).append("\n");
        String returnRegister = generateRegister();
        sb.append(returnRegister).append(" = load ").append(elementType).append(", ").append(elementType).append("* ").append(register).append("\n");
        return returnRegister;
    }
    private String generateUnary(EUnary unaryExp) {
        String value = generateExpression(unaryExp.exp());
        String register = generateRegister();
        switch (unaryExp.op()) {
            case INC -> {sb.append(register).append(" = add ").append(convertType(unaryExp.type())).append(" ").append(value).append(", 1\n");}
            case DEC -> {sb.append(register).append(" = sub ").append(convertType(unaryExp.type())).append(" ").append(value).append(", 1\n");}
        }
         sb.append("store ").append(convertType(unaryExp.type())).append(" ").append(register).append(", ").append(convertType(unaryExp.type())).append("* %").append(((EId) unaryExp.exp()).name()).append("\n");
 
        
        return register;

    }

}
