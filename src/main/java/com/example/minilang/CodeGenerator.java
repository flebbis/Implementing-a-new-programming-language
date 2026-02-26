package com.example.minilang;
import com.example.minilang.Ast.*;

public class CodeGenerator {
    private StringBuilder sb = new StringBuilder();
    private int tempCounter = 0;

    public String Visit(Exp exp){
        if (exp instanceof EInt eInt) {
            return visitInt(eInt);
        } else if (exp instanceof EOpp eOpp) {
            return visitOpp(eOpp);
        } else {
            throw new IllegalArgumentException("Unknown expression type: " + exp.getClass());
        }
    }

    public String visitInt(EInt node) {
        return Integer.toString(node.value());
        }

    public String visitOpp(EOpp node) {
        String left = Visit(node.left());
        String right = Visit(node.right());
        String tempVar = "t" + tempCounter++;
        sb.append(tempVar)
          .append(" = ")
          .append(left)
          .append(" ")
          .append(node.op())
          .append(" ")
          .append(right)
          .append("\n");
        return tempVar;
    }

    





}