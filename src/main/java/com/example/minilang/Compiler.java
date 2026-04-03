package com.example.minilang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
import com.example.minilang.codegen.FunctionCodeGen;
import com.example.minilang.codegen.StatementCodeGen;
import com.example.minilang.typechecker.TypeChecker;

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

    
        String llvmCode = generateLLVM(typeCheckedAst, path.getFileName().toString());
        
        System.out.println("\n===== LLVM IR Code =====");
        System.out.println(llvmCode);
        
        // ===== STEP 5: Write to File =====
        String outputFileName = path.getFileName().toString().replace(".ml", ".ll");
        Path outputPath = path.getParent().resolve(outputFileName);
        Files.writeString(outputPath, llvmCode);
        System.out.println("\nOutput written to: " + outputPath);
    }

        private static String generateLLVM(Ast.Program program, String filename) {
        StringBuilder sb = new StringBuilder();
        StringBuilder globals = new StringBuilder();
        StringBuilder globalStrings = new StringBuilder();
        java.util.HashSet<String> functionVariables = new java.util.HashSet<>();
        com.example.minilang.codegen.Environment environment = new com.example.minilang.codegen.Environment();

        DebugMetaData debugMetaData = new DebugMetaData(filename);

        // ===== External Declarations =====
        sb.append("declare i32 @printf(i8*, ...)\n");
        sb.append("declare double @pow(double, double)\n");
        sb.append("declare i8* @malloc(i64)\n");
        sb.append("declare void @free(i8*)\n");
        sb.append("declare i8* @int_to_string(i32)\n");
        sb.append("declare i8* @double_to_string(double)\n");
        sb.append("declare i8* @bool_to_string(i1)\n");
        sb.append("declare i8* @string_concat(i8*, i8*)\n");
        sb.append("declare i8* @array_int_to_string(i32*, i32)\n");
        sb.append("declare i8* @array_double_to_string(double*, i32)\n");
        sb.append("declare i8* @array_bool_to_string(i1*, i32)\n");
        sb.append("declare i8* @array_string_to_string(i8*, i32)\n");
        sb.append("\n");

        // ===== Format String Constants for print() =====
        sb.append("@.fmt.int = private constant [4 x i8] c\"%d\\0A\\00\"\n");
        sb.append("@.fmt.double = private constant [4 x i8] c\"%f\\0A\\00\"\n");
        sb.append("@.fmt.string = private constant [4 x i8] c\"%s\\0A\\00\"\n");
        sb.append("@.fmt.newline = private constant [2 x i8] c\"\\0A\\00\"\n");
        sb.append("\n");

        int mainId = debugMetaData.createSubProgram("main", 1);
        sb.append("define void @main() !dbg !").append(mainId).append(" {\n");
        sb.append("entry:\n");

        // ===== Generate Code for Global Statements =====
        StatementCodeGen stmtCodegen = new StatementCodeGen(sb, environment, globals, globalStrings, functionVariables, debugMetaData);
        for (Ast.Stmt stmt : program.stmts()) {
            stmtCodegen.generateStatement(stmt);
        }

        sb.append("  ret void\n");
        sb.append("}\n\n");

        // ===== Generate Code for Each Function =====
        FunctionCodeGen funcCodegen = new FunctionCodeGen(sb, environment, globals, globalStrings, functionVariables, stmtCodegen, debugMetaData);
        for (Ast.Func func : program.functions()) {
            funcCodegen.generateFunction(func);
        }

        // Append globals and global strings before the rest
        sb.insert(0, globalStrings.toString());
        sb.insert(0, globals.toString());

        sb.append(debugMetaData.emit());

        return sb.toString();
    }
}