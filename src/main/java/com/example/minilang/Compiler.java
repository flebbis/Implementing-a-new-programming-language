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

    
        String llvmCode = generateLLVM(typeCheckedAst);
        
        System.out.println("\n===== LLVM IR Code =====");
        System.out.println(llvmCode);
        
        // ===== STEP 5: Write to File =====
        String outputFileName = path.getFileName().toString().replace(".ml", ".ll");
        Path outputPath = path.getParent().resolve(outputFileName);
        Files.writeString(outputPath, llvmCode);
        System.out.println("\nOutput written to: " + outputPath);
    }

    private static String generateLLVM(Ast.Program program) {
        StringBuilder sb = new StringBuilder();
        LabelGenerator labelGen = new LabelGenerator();

        // ===== External Declarations =====
        sb.append("declare i32 @printf(i8*, ...)\n");
        sb.append("declare double @pow(double, double)\n");
        sb.append("\n");

        // ===== Format String Constants for print() =====
        sb.append("@.fmt.int = private constant [4 x i8] c\"%d\\0A\\00\"\n");       // "%d\n\0"
        sb.append("@.fmt.double = private constant [4 x i8] c\"%f\\0A\\00\"\n");    // "%f\n\0"
        sb.append("@.fmt.string = private constant [4 x i8] c\"%s\\0A\\00\"\n");    // "%s\n\0"
        sb.append("@.fmt.newline = private constant [2 x i8] c\"\\0A\\00\"\n");      // "\n\0"
        sb.append("\n");

        sb.append("define void @main() {\n");
        sb.append("entry:\n");

        // ===== Generate Code for Global Statements =====
        // (These are top-level statements, if any)
        StatementCodeGen stmtCodegen = new StatementCodeGen(sb, labelGen, program.functions());
        for(Ast.Stmt stmt : program.stmts()) {
            stmtCodegen.codeGenStmt(stmt);
        }

         sb.append("  ret void\n");
        //sb.append("  ret i32 0\n");
        sb.append("}\n\n");

        // ===== Generate Code for Each Function =====
        FunctionCodeGen funcCodegen = new FunctionCodeGen(sb, labelGen, program.functions());
        for(Ast.Func func : program.functions()) {
            funcCodegen.codeGenFunDef(func);
        }



        return sb.toString();
    }
}