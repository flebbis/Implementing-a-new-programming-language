package com.example.minilang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
import com.example.minilang.typechecker.TypeChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.List;

public class Compiler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java -jar compiler.jar <sourcefile>");
            System.exit(1);
        }
        String optLevel = args.length > 1 ? args[1] : "-O3";
        try {
            AnalysisResult result = parseFile(args[0], optLevel);
            System.out.println(objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static AnalysisResult parseFile(String input) throws IOException {
        return parseFile(input, "0");
    }

    public static AnalysisResult parseFile(String input, String optLevel) throws IOException {

 
        // For debugging, needs to be error so not interference with JSON output
        System.err.println("=== COMPILATION START ===");
        System.err.println("Reading: " + input);

        // 2. Infrastructure
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);

        ParseTree tree = parser.program();

        // Build AST
        AstBuilderVisitor astBuilder = new AstBuilderVisitor();
        Ast.Program astRoot = astBuilder.visit(tree);
        // System.err.println("AST: " + astRoot); // Send debug output to stderr

        TypeChecker typeChecker = new TypeChecker();
        Ast.Program typeCheckedAst = typeChecker.typeCheck(astRoot);
        List<InferenceSuggestion> inf = typeChecker.getInferenceSuggestions();
        List<TypeReplacementSuggestion> repl = typeChecker.getTypeReplacementSuggestions();
        return new AnalysisResult(inf, repl);
    }

    public record AnalysisResult(
            List<InferenceSuggestion> inferenceSuggestions,
            List<TypeReplacementSuggestion> typeReplacementSuggestions
    ) {}

}