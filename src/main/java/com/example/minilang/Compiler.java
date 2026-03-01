package com.example.minilang;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
/*
Test File, ville bara se att det printar ut// 
LLVM ska lösa detta
*/
public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java -jar compiler.jar <sourcefile>");
            System.exit(1);
        }
        // läser rad för rad för filen
        String sourceFile = args[0];
        List<String> lines = Files.readAllLines(Paths.get(sourceFile));

        StringBuilder sb = new StringBuilder();
        sb.append("; Generated assembly for: ").append(sourceFile).append("\n\n");
        sb.append("section .text\n");
        sb.append("global main\n\n");
        sb.append("main:\n");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
           // sb.append("    ; Line ").append(i + 1).append(": ").append(line).append("\n");
            sb.append("    mov  eax, ").append(i).append("\n");
        }

        System.out.print(sb);
    }
}
package com.example.minilang;

import org.antlr.v4.runtime.BailErrorStrategy;
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

        //parser.setErrorHandler(new BailErrorStrategy());
        //lexer.removeErrorListeners();
        //lexer.addErrorListener();

        // 3. Parse and create Tree
        ParseTree tree = parser.program();


        // 5. Output
        System.out.println("Equation: " + input);
        System.out.println("Tree: " + tree.toStringTree(parser));

        // 6. Build AST
        AstBuilderVisitor astBuilder = new AstBuilderVisitor();
        Ast.Program astRoot = astBuilder.visit(tree);
        System.out.println("AST:      " + astRoot);
    }
}
