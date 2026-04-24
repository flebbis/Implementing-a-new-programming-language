// src/main/java/com/example/minilang/typechecker/TypeCheckerServer.java
package com.example.minilang.typechecker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.example.minilang.GrammarLexer;
import com.example.minilang.GrammarParser;
import com.example.minilang.TypeError;
import com.example.minilang.ast.Ast;
import com.example.minilang.ast.AstBuilderVisitor;
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
    //private static final Gson gson = new Gson();
    private static final TypeChecker typeChecker = new TypeChecker();

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
    public static TypeError extractErrorInfo(TypeException e) {
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


}