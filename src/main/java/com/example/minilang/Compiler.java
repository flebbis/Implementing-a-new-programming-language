package com.example.minilang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
import com.example.minilang.typechecker.TypeChecker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java -jar compiler.jar <sourcefile>");
            System.exit(1);
        }
        String optLevel = args.length > 1 ? args[1] : "-O3";
        parseFile(Path.of(args[0]), optLevel);

    }

    public static void parseFile(Path path) throws IOException {
        parseFile(path, "0");
    }

     public static void parseFile(Path path, String optLevel) throws IOException {

        String input = Files.readString(path);

        // 2. Infrastructure
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);

        //parser.setErrorHandler(new BailErrorStrategy());
        //lexer.removeErrorListeners();
        //lexer.addErrorListener();

        // 3. Parse and create Tree
        ParseTree tree = parser.program();


        // 5. Output
      //  System.out.println("Equation: " + input);
        //System.out.println("Tree: " + tree.toStringTree(parser));

        // 6. Build AST
        AstBuilderVisitor astBuilder = new AstBuilderVisitor();
        Ast.Program astRoot = astBuilder.visit(tree);
        System.out.println("AST:      " + astRoot);

        TypeChecker typeChecker = new TypeChecker();
        Ast.Program typeCheckedAst = typeChecker.typeCheck(astRoot);

    }


}