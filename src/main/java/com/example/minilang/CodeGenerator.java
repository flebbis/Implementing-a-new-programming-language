package com.example.minilang;


public class CodeGenerator {



    private StringBuilder sb = new StringBuilder();
    private int tempCounter = 0;

    public String getCode() {
    return sb.toString();
    }
    public String Generate(Ast.Program p) {
        sb.append("define i32 @main() {\n");
        String result ="";
        for (Ast.Stmt stmt : p.stmts()) {
            if (stmt instanceof Ast.SExp sExp) {
                result = Visit(sExp.exp());
            }
        }
        sb.append("    ret i32 ").append(result).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    public String Visit(Ast.Exp exp){
        if (exp instanceof Ast.EInt eInt) {
            return visitInt(eInt);
        } else if (exp instanceof Ast.EOpp eOpp) {
            return visitOpp(eOpp);
        } else {
            throw new IllegalArgumentException("Unknown expression type: " + exp.getClass());
        }
    }
    

    public String visitInt(Ast.EInt node) {
        return Integer.toString(node.value());
        }

        public String visitOpp(Ast.EOpp node) {
            String left = Visit(node.left());
            String right = Visit(node.right());
            String tempVar = "%t" + tempCounter++;
            sb.append("    ").append(tempVar)
              .append(" = add i32 ")
              .append(left).append(", ")
              .append(right).append("\n");
            return tempVar;
        }
}