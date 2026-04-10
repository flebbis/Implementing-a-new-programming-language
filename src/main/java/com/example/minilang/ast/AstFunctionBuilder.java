package com.example.minilang.ast;

import com.example.minilang.*;

import java.util.ArrayList;
import java.util.List;

public class AstFunctionBuilder extends GrammarBaseVisitor<Ast.Func> {

    private final AstStatementBuilder astStatementBuilder = new AstStatementBuilder();

    @Override
    public Ast.Func visitDFunc(GrammarParser.DFuncContext ctx) {
        // DFunc contains a single FuncContext (either FuncNoInference or FuncInference)
        GrammarParser.FuncContext func = ctx.func();
        return visitFunc(func);
    }

    @Override
    public Ast.Func visitFunc(GrammarParser.FuncContext ctx) {
        System.out.println(ctx.getText());
        // rule: TYPE 'func' ID '(' paramSeparator ')' block

        Ast.Type returnType = new Ast.TUnknown(); // Default to TUnknown if no return type is specified
        if(ctx.typeAnnotation() != null) {
            String returnTypeText = ctx.typeAnnotation().getText();
            returnType = TypeConverter.mapType(returnTypeText);
        }

        // Function ID
        String name = ctx.ID().getText();

        // Parameters
        List<Ast.Arg> params = new ArrayList<>();
        GrammarParser.ParamSeparatorContext parameterContext = ctx.paramSeparator();
        if (parameterContext != null && parameterContext.param() != null) {
            // Loop through each parameter and build the corresponding AST nodes
            for (GrammarParser.ParamContext p : parameterContext.param()) {
                Pos pos = new Pos(p.getStart().getLine(), p.getStart().getCharPositionInLine());
                //TODO: idk om denna if checken e helt rätt men något måste checkas iaf
                if (p.TYPE() != null) {
                    String pTypeText = p.TYPE().getText(); // Parameter type for param p
                    Ast.Type pType = TypeConverter.mapType(pTypeText); // Map the parameter type string to our Ast.Type enum
                    String id = p.ID().getText();
                    params.add(new Ast.Arg(id, pType, pos));
                }
                else{
                    params.add(new Ast.Arg(p.ID().getText(), new Ast.TUnknown(), pos));

                }

            }
        }

        // Function body
        Ast.Stmt body = null;
        if (ctx.block() != null) {
            body = astStatementBuilder.visitBlock(ctx.block());
        }

        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.Func(name, params, returnType, body, pos);
    }





}
