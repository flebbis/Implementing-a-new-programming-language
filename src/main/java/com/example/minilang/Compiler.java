package com.example.minilang;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compiler {

    public static void parseFile(Path path) throws IOException {
        String input = Files.readString(path);

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

        // 6. Build AST
        AstBuilderVisitor astBuilder = new AstBuilderVisitor();
        Ast.Exp astRoot = astBuilder.visit(tree);
        System.out.println("AST:      " + astRoot);
    }
}
