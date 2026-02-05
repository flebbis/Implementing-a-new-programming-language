package com.example.minilang;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.Map;

public class MiniLanguageMain {
    public static void main(String[] args) {
        String input = "3 + 5 == 8";

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        ParseTree tree = parser.program();

        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor();
        typeChecker.visit(tree);
        Map<ParseTree, Type> annotations = typeChecker.getNodeTypes();


        System.out.println("--- Annotated Tree (Lisp Style) ---");
        System.out.println(toLispStyleString(tree, annotations));
        System.out.println("-----------------------------------");


        EvalVisitor eval = new EvalVisitor();
        Object result = eval.visit(tree);
        System.out.println("Result: " + result);
    }

    private static String toLispStyleString(ParseTree node, Map<ParseTree, Type> annotations) {
        if (node.getChildCount() == 0) {
            return node.getText();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("(");


        String name = node.getClass().getSimpleName().replace("Context", "");

        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        sb.append(name);

        // Append Type if it exists
        Type type = annotations.get(node);
        if (type != null) {
            sb.append(":").append(type);
        }

        // Process children
        for (int i = 0; i < node.getChildCount(); i++) {
            sb.append(" ");
            sb.append(toLispStyleString(node.getChild(i), annotations));
        }

        sb.append(")");

        return sb.toString();
    }
}