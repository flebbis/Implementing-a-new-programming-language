package com.example.minilang;

import com.example.minilang.GrammarParser.*;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.IdentityHashMap;
import java.util.Map;

public class TypeCheckerVisitor extends GrammarBaseVisitor<Type> {
    //Annotated tree kinda
    private final Map<ParseTree, Type> nodeTypes = new IdentityHashMap<>();

    public Map<ParseTree, Type> getNodeTypes() {
        return nodeTypes;
    }

    // Helper method to annotate the tree as we walk it
    private Type annotate(ParseTree ctx, Type type) {
        nodeTypes.put(ctx, type);
        return type;
    }

    @Override
    public Type visitInt(IntContext ctx) {
        return annotate(ctx, Type.INT);
    }

    @Override
    public Type visitBool(BoolContext ctx) {
        return annotate(ctx, Type.BOOL);
    }

    @Override
    public Type visitParens(ParensContext ctx) {
        return annotate(ctx, visit(ctx.exp()));
    }

    @Override
    public Type visitAddSub(AddSubContext ctx) {
        Type left = visit(ctx.left);
        Type right = visit(ctx.right);

        if (left == Type.INT && right == Type.INT) {
            return annotate(ctx, Type.INT);
        }
        throw new RuntimeException("Type Error: Cannot Add/Sub " + left + " and " + right);
    }

    @Override
    public Type visitMulDiv(MulDivContext ctx) {
        Type left = visit(ctx.left);
        Type right = visit(ctx.right);

        if (left == Type.INT && right == Type.INT) {
            return annotate(ctx, Type.INT);
        }
        throw new RuntimeException("Type Error: Cannot Multiply/Divide " + left + " and " + right);
    }

    @Override
    public Type visitEq(EqContext ctx) {
        Type left = visit(ctx.left);
        Type right = visit(ctx.right);

        if (left == right) { // e.g. INT==INT or BOOL==BOOL
            return annotate(ctx, Type.BOOL);
        }
        throw new RuntimeException("Type Error: Cannot compare " + left + " with " + right);
    }

    @Override
    public Type visitProgram(ProgramContext ctx) {
        return annotate(ctx, visit(ctx.exp()));
    }
}
