// src/main/java/com/example/minilang/typechecker/TypeCheckerServer.java
package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
import com.example.minilang.GrammarLexer;
import com.example.minilang.GrammarParser;
import com.google.gson.Gson;
import org.antlr.v4.runtime.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This works as the bridge between TypeScript LSP and Java type checker
 * 
 * This server acts as a standalone executable that accepts source code as input,
 * runs the type checker, and returns type errors as JSON
 * It's designed to be called by the TypeScript LSP server via child_process.spawn()
 * 
 * Communication pattern: 
 * - Input - source code passed as command-line argument
 * - Output - JSON array of type errors printed to stdout
 * - Errors - printed to stderr for debugging
 */
public class TypeCheckerServer {
    private static final Gson gson = new Gson();
    private static final TypeChecker typeChecker = new TypeChecker();

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Usage: java -jar typechecker.jar \\\"<source-code>\\\"\"");
            System.exit(1);
            return;
        }
        String source = args[0];
        List<TypeError> errors = checkSource(source);
        System.out.println(gson.toJson(errors));
    }

    /**
     * Type checks a source code string and returns any type errors found.
     * 
     * The process:
     * 1. Parse source code using ANTLR to get a parse tree
     * 2. Build an AST using the AstBuilderVisitor
     * 3. Run the type checker on the AST
     * 4. Catch any TypeException and convert to structured error format
     * 
     * @param source The complete source code to type check
     * @return List of TypeError objects (empty if no errors found)
     */

    private static List<TypeError> checkSource(String source) {
        List<TypeError> errors = new ArrayList<>();
        try {
            // Lexical analysis - convert source to tokens
            CharStream input = CharStreams.fromString(source);
            GrammarLexer lexer = new GrammarLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // Syntactic analysis - build parse tree
            GrammarParser parser = new GrammarParser(tokens);
            
            // AST construction - convert parse tree to abstract syntax tree
            AstBuilderVisitor astBuilder = new AstBuilderVisitor();
            Ast.Program program = astBuilder.visitProgram(parser.program());
            
            // Semantic analysis - type checking
            typeChecker.typeCheck(program);
            
        } catch (TypeException e) {
            // Type error found - convert to structured format for LSP
            errors.add(extractErrorInfo(e));
        } catch (Exception e) {
            // Unexpected error (parse failture, internal error, etc)
            errors.add(new TypeError("Internal error: " + e.getMessage(), 0, 0));
        }
        return errors;
    }


    /**
     * Converts a TypeException into a structured TypeError object
     * 
     * This method does the following
     * 1. Extracts line and column number using regex
     * 2. Removes the position suffix from the message
     * 3. Creates a TypeError with clean message and position info
     * 
     * @param e the TypeException thrown by the type checker
     * @return TypeError with extracted position and clean message
     */
    private static TypeError extractErrorInfo(TypeException e) {
        String message = e.getMessage();
        int line = 0;
        int column = 0;
        
        // Extract line and column using regex pattern
        Pattern pattern = Pattern.compile("line (\\d+), column (\\d+)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            line = Integer.parseInt(matcher.group(1));
            column = Integer.parseInt(matcher.group(2));
        }
        
        // Clean message by removing the position suffix
        String cleanMessage = message.replaceAll(" \\(line \\d+, column \\d+\\)", "");
        
        return new TypeError(cleanMessage, line, column);
    }

    /**
     * Error class for JSON serialization
     * 
     * This class matches the TypeError interface in TypeCheckerBridge.ts
     * Gson automatically converts this to JSON when returned to LSP
     * */ 
    static class TypeError {
        String message;
        int line;
        int column;
        int endLine;
        int endColumn;
        String severity;
        
        TypeError(String message, int line, int column) {
            this.message = message;
            this.line = line;
            this.column = column;
            this.endLine = line;
            this.endColumn = column + 1;
            this.severity = "error";
        }
    }
}