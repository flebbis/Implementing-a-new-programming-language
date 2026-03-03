package com.example.minilang.ast; // AST Definition

import com.example.minilang.Pos;

import java.util.List;

/**
 * One file to rule them all.
 * This defines the entire structure of your Abstract Syntax Tree.
 */
public class Ast {

    public record Program(List<Stmt> stmts, List<Func> functions) {}

    public record Func(String name, List<Exp> params, Type returnType, Stmt body, Pos pos) {}

    // The base type for all nodes
    // 'sealed' = only the specific records listed below can implement this.
    public sealed interface Exp extends HasPos, HasType permits EInt, EDouble, EString, EBool, EId,
            EInc, EDec, ECall, ENot, EPower, EOpp, ELt, EGt, EGe, ELe, ENe, EEq, EAnd, EOr,
            EAss, EPlusAss, EMinusAss, EDivAss, EMultAss, EArrayIndex {}

    public sealed interface Stmt extends HasPos permits BlockStmt, SimpleStmt  {}
    public sealed interface BlockStmt extends Stmt permits SWhile, SDo, SIf, SBlock {}
    public sealed interface SimpleStmt extends Stmt permits SDecl, SInit, SReturn, SExp {}

    // A leaf node representing a number (e.g., 3)
    public record EInt(int value, Type type, Pos pos) implements Exp {}

    // A node representing an operation (e.g., 3 + 5)
    public record EOpp(Exp left, Exp right, Op op, Type type, Pos pos) implements Exp {}
    public record EDouble(double value, Type type, Pos pos) implements Exp {}
    public record EString(String value, Type type, Pos pos) implements Exp {}
    public record EBool(boolean value, Type type, Pos pos) implements Exp {}
    public record EId(String name, Type type, Pos pos) implements Exp {}
    public record EInc(Exp exp, Type type, Pos pos) implements Exp {}
    public record EDec(Exp exp, Type type, Pos pos) implements Exp {}
    public record ECall(Exp exp, List<Exp> args, Type type, Pos pos) implements Exp {}
    public record ENot(Exp exp, Type type, Pos pos) implements Exp {}
    public record EPower(Exp base, Exp exponent, Type type, Pos pos) implements Exp {}
    public record ELt(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record EGt(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record EGe(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record ELe(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record ENe(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record EEq(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record EAnd(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record EOr(Exp left, Exp right, Type type, Pos pos) implements Exp {}
    public record EAss(String name, Exp value, Type type, Pos pos) implements Exp {}
    public record EPlusAss(String name, Exp value, Type type, Pos pos) implements Exp {}
    public record EMinusAss(String name, Exp value, Type type, Pos pos) implements Exp {}
    public record EDivAss(String name, Exp value, Type type, Pos pos) implements Exp {}
    public record EMultAss(String name, Exp value, Type type, Pos pos) implements Exp {}
    public record EArrayIndex(Exp array, Exp index, Type type, Pos pos) implements Exp {}

    // The operators we support
    public enum Op {
        ADD, SUB, MUL, DIV, NOT, MOD
    }

    public enum Type {
        TInt, TBool, TString, TDouble, TUnknown;
    }

    public interface HasPos { Pos pos(); }
    public interface HasType { Type type(); }


    public record SWhile(Exp condition, Stmt body, Pos pos) implements BlockStmt {}
    public record SDo(Exp times, Stmt body, Pos pos) implements BlockStmt {}
    public record SIf(Exp condition, Stmt thenBranch, Stmt elseBranch, Pos pos) implements BlockStmt {}
    public record SBlock(List<Stmt> statements, Pos pos) implements BlockStmt {}

    public record SDecl(Type type, String name, Pos pos) implements SimpleStmt {}
    public record SInit(Type type, String name, Exp value, Pos pos) implements SimpleStmt {}
    public record SReturn(Exp value, Pos pos) implements SimpleStmt {}
    public record SExp(Exp exp, Pos pos) implements SimpleStmt {}

}