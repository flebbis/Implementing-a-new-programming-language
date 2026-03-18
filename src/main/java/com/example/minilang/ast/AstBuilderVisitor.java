package com.example.minilang.ast; // Builder Visitor

import com.example.minilang.GrammarBaseVisitor;
import com.example.minilang.GrammarParser.*;

/**
 * Transforms the raw Parse Tree (CST) into our nice, clean AST.
 */

import java.util.ArrayList;
import java.util.List;

public class AstBuilderVisitor extends GrammarBaseVisitor<Ast.Program> {

    AstStatementBuilder astStatementBuilder = new AstStatementBuilder();
    AstFunctionBuilder astFunctionBuilder = new AstFunctionBuilder();

    @Override
    public Ast.Program visitProgram(ProgramContext ctx) {
        List<DefContext> defContext = ctx.def();
        List<Ast.Stmt> stmts = new ArrayList<>();
        List<Ast.Func> funcs = new ArrayList<>();

        // Loop through the definitions in the program and build the corresponding AST nodes
        for(DefContext def : defContext) {
            if(def instanceof DStmContext) {
                stmts.add(astStatementBuilder.visitDStm((DStmContext) def));
            } else if(def instanceof DFuncContext) {
                funcs.add(astFunctionBuilder.visitDFunc((DFuncContext) def));
            }
        }

        return new Ast.Program(stmts, funcs);
    }


}
