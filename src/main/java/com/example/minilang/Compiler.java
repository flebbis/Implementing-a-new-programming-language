package com.example.minilang;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
/*
Test File, ville bara se att det printar ut// AI
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

        StringBuilder asm = new StringBuilder();
        asm.append("; Generated assembly for: ").append(sourceFile).append("\n\n");
        asm.append("section .text\n");
        asm.append("global main\n\n");
        asm.append("main:\n");
        asm.append("    push rbp\n");
        asm.append("    mov  rbp, rsp\n\n");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            asm.append("    ; Line ").append(i + 1).append(": ").append(line).append("\n");
            asm.append("    mov  eax, ").append(i).append("\n");
        }

        asm.append("\n    mov  eax, 0\n");
        asm.append("    pop  rbp\n");
        asm.append("    ret\n");

        System.out.print(asm);
    }
}