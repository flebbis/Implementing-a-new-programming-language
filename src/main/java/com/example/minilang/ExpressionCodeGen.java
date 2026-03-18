package com.example.minilang;

import com.example.minilang.Ast.*;
import static com.example.minilang.RegisterGenerator.generateRegister;
public class ExpressionCodeGen extends Helper {

    private StringBuilder sb;
    public ExpressionCodeGen(StringBuilder sb) {
        this.sb = sb;
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
        return "temp";
    }
    private String generateBool(EBool boolExp) {
        return String.valueOf(boolExp.value());
    }
    private String generateId(EId idExp) {
        String register = generateRegister();
        sb.append("%").append(idExp.name()).append("_value").append(register).append(" = load ").append(convertType(idExp.type())).append(", ").append(convertType(idExp.type())).append("* %").append(idExp.name()).append("\n");
        return "%" + idExp.name() + "_value" + register;

    }
    private String generateCall(ECall callExp) {
        return "temp";

    }
    private String generateNot(ENot notExp) {
        return "temp";

    }
    private String generatePower(EPower powerExp) {
        return "temp";

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
            case NOT -> {}
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
        return "temp";

    }
    private String generateAss(EAss assExp) {
        return "temp";

    }
    private String generateArrayIndex(EArrayIndex arrayIndexExp) {
        return "temp";

    }
    private String generateUnary(EUnary unaryExp) {
        return "temp";

    }

}