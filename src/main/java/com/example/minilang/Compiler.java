package com.example.minilang;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java -jar compiler.jar <sourcefile>");
            System.exit(1);
        }
        parseFile(Path.of(args[0]));
   
    }

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
      //  System.out.println("Equation: " + input);
        //System.out.println("Tree: " + tree.toStringTree(parser));

        // 6. Build AST
        AstBuilderVisitor astBuilder = new AstBuilderVisitor();
        Ast.Program astRoot = astBuilder.visit(tree);
        //System.out.println("AST:      " + astRoot);

        // generate llvm ir to a string
        CodeGenerator codeGen = new CodeGenerator();
        String ir = codeGen.Generate(astRoot);
        // a new os process that can process the llvm ir, llc which is for assembly and -filetype=asm 
        // what llc should output which is assembly
        ProcessBuilder pb = new ProcessBuilder("llc", "-filetype=asm, -0=1");
        //starts a process
        Process process = pb.start();

        //llc stdin gerenate LLVm, convert it to bytes and then close it 
        process.getOutputStream().write(ir.getBytes());
        process.getOutputStream().close();

        // creats a Inputstream and read the assembly from llc stdout, convert it to string 
        InputStream is = process.getInputStream();
        byte[] bytes = is.readAllBytes();
        String assembly = new String(bytes,StandardCharsets.UTF_8);

        System.out.print(assembly);
        
        };
    }
    

