package com.example.minilang;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MiniLanguageMain {
    public static void main(String[] args) {
        // 1. Definition
        String input = "3 + 5 * 2";

        // 2. Infrastructure
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);

        // 3. Parse and create Tree
        ParseTree tree = parser.program();

        // 4. Visit the tree to calculate result
        EvalVisitor eval = new EvalVisitor();
        Integer result = eval.visit(tree);

        // 5. Output
        System.out.println("Equation: " + input);
        System.out.println("Tree: " + tree.toStringTree(parser));
        System.out.println("Result:   " + result);
    }
}
