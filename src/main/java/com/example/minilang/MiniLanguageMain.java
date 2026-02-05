package com.example.minilang;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MiniLanguageMain {
    public static void main(String[] args) {
        String input = "3 + 5 * 2";

        // 1. Use the simple name GrammarLexer (no prefixes needed)
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(input));

        // 2. Create a stream of tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 3. Use the simple name GrammarParser
        GrammarParser parser = new GrammarParser(tokens);

        // 4. Start parsing from your 'program' rule
        ParseTree tree = parser.program();

        // 5. Print the tree
        System.out.println(tree.toStringTree(parser));
    }
}
