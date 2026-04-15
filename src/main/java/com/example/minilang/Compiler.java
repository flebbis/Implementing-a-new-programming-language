package com.example.minilang;

import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
import com.example.minilang.codegen.CodeGenerator;
import com.example.minilang.typechecker.TypeChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Compiler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java -jar compiler.jar <source-text> <source-filepath>");
            System.exit(1);
        }
        String optLevel = args.length > 2 ? args[1] : "-O3";
        String filePath = args[1];
        try {
            List<InferenceSuggestion> suggestions = parseFile(args[0], filePath, optLevel);
            // Output as JSON to stdout for the language server to parse
            System.out.println(objectMapper.writeValueAsString(suggestions));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static List<InferenceSuggestion> parseFile(String input, String strPath, String optLevel) throws IOException {

        // For debugging, needs to be error so not interference with JSON output
        System.err.println("=== COMPILATION START ===");
        System.err.println("Reading: " + input);
        System.err.println("From: " + strPath);

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
        //System.out.println("Tree: " + tree.toStringTree(parser));

        // 6. Build AST
        AstBuilderVisitor astBuilder = new AstBuilderVisitor();
        Ast.Program astRoot = astBuilder.visit(tree);
        System.err.println("unchecked AST: " + astRoot + " \n\n");
        //System.out.println("AST:      " + astRoot + "\n\n");

        TypeChecker typeChecker = new TypeChecker();
        Ast.Program typeCheckedAst = typeChecker.typeCheck(astRoot);
        System.err.println(typeCheckedAst);
        List<InferenceSuggestion> suggestions = typeChecker.getInferenceSuggestions();

        Path path = Path.of(strPath);
        String outputFileName = path.getFileName().toString().replace(".fika", ".ll");
        Path outputPath = path.getParent().resolve(outputFileName);
        String llvmCode = generateLLVM(typeCheckedAst);
        Files.writeString(outputPath, llvmCode);
        System.err.println("\nOutput written to: " + outputPath);
        return suggestions;
    }

    // 7. Code Generation
    private static String generateLLVM(Ast.Program program) {
        StringBuilder sb = new StringBuilder();
        StringBuilder globals = new StringBuilder();

        // ===== External Declarations =====
        /*sb.append("declare i32 @printf(i8*, ...)\n");
        sb.append("declare double @pow(double, double)\n");
        sb.append("\n");

        // ===== Format String Constants for print() =====
        sb.append("@.fmt.int = private constant [4 x i8] c\"%d\\0A\\00\"\n");       // "%d\n\0"
        sb.append("@.fmt.double = private constant [4 x i8] c\"%f\\0A\\00\"\n");    // "%f\n\0"
        sb.append("@.fmt.string = private constant [4 x i8] c\"%s\\0A\\00\"\n");    // "%s\n\0"
        sb.append("@.fmt.newline = private constant [2 x i8] c\"\\0A\\00\"\n");      // "\n\0"
        sb.append("\n");*/



        // ===== Generate Code for Global Statements =====
        // (These are top-level statements, if any)




        CodeGenerator codeGen = new CodeGenerator(sb, globals);
        codeGen.generate(program);

        // ===== Generate Code for Each Function =====



        return globals.append(sb).toString();
    }
}
