package com.example.minilang;

import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
import com.example.minilang.codegen.CodeGenerator;
import com.example.minilang.typechecker.TypeChecker;
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
        System.out.println("AST:      " + astRoot + "\n\n");

        TypeChecker typeChecker = new TypeChecker();
        Ast.Program typeAnnotatedProgram = typeChecker.typeCheck(astRoot);
        System.out.println("Type Annotated AST: " + typeAnnotatedProgram);

        String outputFileName = path.getFileName().toString().replace(".ml", ".ll");
        Path outputPath = path.getParent().resolve(outputFileName);
        String llvmCode = generateLLVM(typeAnnotatedProgram);
        Files.writeString(outputPath, llvmCode);
        System.out.println("\nOutput written to: " + outputPath);
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
