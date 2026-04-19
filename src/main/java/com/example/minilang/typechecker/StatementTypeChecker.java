package com.example.minilang.typechecker;

import com.example.minilang.TypeConverter;
import com.example.minilang.TypeReplacementSuggestion;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.minilang.InferenceSuggestion;

public class StatementTypeChecker {

    private Context context;
    private ExpressionTypeChecker expressionTypeChecker;
    private String currentFunction;
    private HashMap<String, Signature> functionSignatures;
    private Context inferenceContext;
    private List<InferenceSuggestion> inferenceSuggestions;
    private List<TypeReplacementSuggestion> typeReplacementSuggestions;
    private HashMap<String, List<String>> functionBindings;

    public StatementTypeChecker(Context context, HashMap<String, Signature> functionSignatures,
                                Context inferenceContext, List<InferenceSuggestion> inferenceSuggestions,
                                List<TypeReplacementSuggestion> typeReplacementSuggestions, HashMap<String, List<String>> functionBindings) {
        this.context = context;
        this.functionSignatures = functionSignatures;
        this.functionBindings = functionBindings;
        this.expressionTypeChecker = new ExpressionTypeChecker(context, functionSignatures, functionBindings);
        this.inferenceContext = inferenceContext;
        this.inferenceSuggestions = inferenceSuggestions;
        this.typeReplacementSuggestions = typeReplacementSuggestions;
    }

    public Ast.Stmt typeCheck(Ast.Stmt stmt) {
        return switch (stmt) {
            case Ast.SWhile SWhile -> typeCheck(SWhile);
            case Ast.SDo SDo -> typeCheck(SDo);
            case Ast.SReturn SRet -> typeCheck(SRet);
            case Ast.SIf SifStmt -> typeCheck(SifStmt);
            case Ast.SBlock SBlock -> typeCheck(SBlock);
            case Ast.SDecl SDecl -> typeCheck(SDecl);
            case Ast.SInit SInit -> typeCheck(SInit);
            case Ast.SExp SExp -> typeCheck(SExp);
        };
    }

    public Ast.Stmt typeCheck(Ast.SInit sInit) {
        Ast.Exp value = expressionTypeChecker.typeCheck(sInit.value());
        Ast.Type typeToCheck = sInit.type();

        // Logical Check: Is this a redeclaration or an assignment?
        // Look up if the variable exists in the current scope chain.
        Binding existingBinding = context.lookupLatestBinding(sInit.name());

        // If the variable exists and this is an implicit statement (e.g. "x = 5" not "int x = 5"),
        // treat it as an Assignment to the existing variable.
        if (existingBinding != null && typeToCheck instanceof Ast.TUnknown) {
            // Transform to an expression
            Ast.EAss assign = new Ast.EAss(sInit.name(), value, Ast.AssOp.ASSIGN,
                    existingBinding.inferredType, sInit.pos());
            return new Ast.SExp(expressionTypeChecker.typeCheck(assign), sInit.pos());
        }

        // If we reach here, it is a new Declaration.

        // Infer type if unknown
        if (typeToCheck instanceof Ast.TUnknown) {
            if(!(value.type() instanceof Ast.TUnknown)) {
                typeToCheck = value.type();
                InferenceSuggestion inferenceSuggestion = new InferenceSuggestion(
                        sInit.name(),
                        TypeConverter.typeToString(typeToCheck),
                        sInit.pos().line,
                        sInit.pos().column,
                        sInit.pos().line,
                        sInit.pos().column + sInit.name().length(),
                        "Suggestion: " + TypeConverter.typeToString(typeToCheck) + " " + sInit.name());

                // Avoid duplicates for multiple inference passes
                if(!inferenceSuggestions.contains(inferenceSuggestion)) {
                    inferenceSuggestions.add(inferenceSuggestion);
                }
            }
        }

        // Verify types match for explicit declarations
        // Allow TUnknown values to bypass checks during inference pass
        if (!typeToCheck.equals(value.type()) && !(value.type() instanceof Ast.TUnknown)) {
            if (typeToCheck instanceof Ast.TDouble && value.type() instanceof Ast.TInt) {
                value = new Ast.EDInt(value, value.type(), value.pos());
            } else {
                String currentTypeStr = TypeConverter.typeToString(typeToCheck);
                String newTypeStr = TypeConverter.typeToString(value.type());

                TypeReplacementSuggestion typeReplacementSuggestion = new TypeReplacementSuggestion(
                        sInit.name(),
                        currentTypeStr,
                        sInit.pos().line,
                        sInit.pos().column,
                        sInit.pos().line,
                        sInit.pos().column + currentTypeStr.length(),
                        newTypeStr
                );

                if(!typeReplacementSuggestions.contains(typeReplacementSuggestion)) {
                    typeReplacementSuggestions.add(typeReplacementSuggestion);
                }
//                throw new TypeException(
//                        "Incorrect initializer type, expected type " + TypeConverter.typeToString(typeToCheck) +
//                                " but got " + TypeConverter.typeToString(value.type()),
//                        value.pos());
            }
        }

        // Create binding for this variable
        String bindingId = context.createBinding(
                sInit.name(),
                Binding.Kind.VARIABLE,
                sInit.pos(),
                sInit.type(),           // declared type (may be TUnknown)
                typeToCheck,            // inferred type
                !(sInit.type() instanceof Ast.TUnknown)  // explicit?
        );

        // Extract root variable from expression and add dependency
        String rootBindingId = extractRootVariableId(value);
        if (rootBindingId != null) {
            context.addDependency(bindingId, rootBindingId);
        }

        return new Ast.SInit(typeToCheck, sInit.name(), value, sInit.pos());
    }

    public Ast.SExp typeCheck(Ast.SExp stmt) {
        Ast.Exp exp = expressionTypeChecker.typeCheck(stmt.exp());
        return new Ast.SExp(exp, stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SDecl sDecl) {
        Ast.Type type = new Ast.TUnknown();

        if (sDecl.type() instanceof Ast.TUnknown) {
            // If unknown, try checking the inference context for the type
            int scopeLvl = context.getScopeLevel();

            Binding inferredBinding = inferenceContext.lookupFromScopeLevel(sDecl.name(), scopeLvl);
            if (inferredBinding != null && !(inferredBinding.inferredType instanceof Ast.TUnknown)) {
                type = inferredBinding.inferredType;
                // Add to suggestions for language server
                System.err.println("Found type for " + sDecl.name() + " in inference context: " + TypeConverter.typeToString(type));
                inferenceSuggestions.add(new InferenceSuggestion(
                        sDecl.name(),
                        TypeConverter.typeToString(type),
                        sDecl.pos().line,
                        sDecl.pos().column,
                        sDecl.pos().line,
                        sDecl.pos().column + sDecl.name().length(),
                        "Suggestion: " + TypeConverter.typeToString(type) + " " + sDecl.name()));
            }
        } else {
            type = sDecl.type();
        }

        // Create binding for this declaration
        String bindingId = context.createBinding(
                sDecl.name(),
                Binding.Kind.VARIABLE,
                sDecl.pos(),
                sDecl.type(),           // declared type
                type,                   // inferred type
                !(sDecl.type() instanceof Ast.TUnknown)  // explicit?
        );

        return new Ast.SDecl(type, sDecl.name(), sDecl.pos());
    }

    public Ast.Stmt typeCheck(Ast.SWhile stmt) {
        context.pushNewScope();
        Ast.Exp condition = expressionTypeChecker.typeCheck(stmt.condition());
        if (!(condition.type() instanceof Ast.TBool)) {
            throw new TypeException("Condition of while loop must be of type bool", condition.pos());
        }

        List<Ast.Stmt> bodyStmts = new ArrayList<>();
        // Check body
        if (!(stmt.body() instanceof Ast.SBlock)) {
            throw new TypeException("Body of while loop must be a block statement", stmt.body().pos());
        }
        for (Ast.Stmt s : ((Ast.SBlock) stmt.body()).statements()) {
            bodyStmts.add(typeCheck(s));
        }
        context.popScope();
        return new Ast.SWhile(condition, new Ast.SBlock(bodyStmts, stmt.body().pos()), stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SDo stmt) {
        // Check times expression, should be int
        Ast.Exp exp = expressionTypeChecker.typeCheck(stmt.times());
        if (!(exp.type() instanceof Ast.TInt)) {
            throw new TypeException("Expression in do statement must be of type int, type "
                    + TypeConverter.typeToString(exp.type()) + " was provided", exp.pos());
        }

        // Check body
        Ast.Stmt body = typeCheck(stmt.body());
        if (!(body instanceof Ast.SBlock)) {
            throw new TypeException("Body of do statement must be a block statement", stmt.body().pos());
        }

        return new Ast.SDo(exp, body, stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SReturn stmt) {
        Ast.Exp value = expressionTypeChecker.typeCheck(stmt.value());
        if (currentFunction == null) {
            throw new TypeException("Return statement not inside a function", stmt.pos());
        }
        Signature signature = functionSignatures.get(currentFunction);

        // Inference: If return type is TUnknown, infer it from the return value
        if (signature.returnType instanceof Ast.TUnknown && !(value.type() instanceof Ast.TUnknown)) {
            signature.setReturnType(value.type());
            signature.isInference = true;
        }

        if (!signature.returnType.equals(value.type())) {
            // Allow implicit conversion from int to double
            if (signature.returnType instanceof Ast.TDouble && value.type() instanceof Ast.TInt) {
                value = new Ast.EDInt(value, value.type(), value.pos());
            } else {
                throw new TypeException("Function returns type " + value.type()
                        + ", does not match declared function return type " + signature.returnType, stmt.pos());
            }
        }

        return new Ast.SReturn(value, stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SIf stmt) {
        Ast.Exp condition = expressionTypeChecker.typeCheck(stmt.condition());

        if (!(condition.type() instanceof Ast.TBool)) {
            throw new TypeException("Condition of if statement must be of type bool", stmt.condition().pos());
        }

        // Check then branch
        if (!(stmt.thenBranch() instanceof Ast.SBlock)) {
            throw new TypeException("Then branch of if statement must be a block statement", stmt.thenBranch().pos());
        }

        context.pushNewScope();
        Ast.Stmt thenStmt = typeCheck(stmt.thenBranch());
        context.popScope();

        Ast.Stmt elseStmt = null;
        if (stmt.elseBranch() != null) {
            if (!(stmt.elseBranch() instanceof Ast.SBlock)) {
                throw new TypeException("Else branch of if statement must be a block statement",
                        stmt.elseBranch().pos());
            }
            context.pushNewScope();
            elseStmt = typeCheck(stmt.elseBranch());
            context.popScope();
        }

        return new Ast.SIf(condition, thenStmt, elseStmt, stmt.pos());
    }

    public Ast.SBlock typeCheck(Ast.SBlock stmt) {
        context.pushNewScope();
        List<Ast.Stmt> checkedStmts = new ArrayList<>();
        for (Ast.Stmt s : stmt.statements()) {
            checkedStmts.add(typeCheck(s));
        }
        context.popScope();

        return new Ast.SBlock(checkedStmts, stmt.pos());
    }

    public void setCurrentFunction(String currentFunction) {
        this.currentFunction = currentFunction;
    }

    public void updateInferenceContext(Context context) {
        inferenceContext = context;
    }

    /**
     * Extract the root variable id from an expression.
     * For now, handles:
     * - EId: direct variable reference
     * - Other expressions: recursively extracts from subexpressions
     *
     * Returns null if no variable dependency is found.
     * For complex expressions, this could be enhanced to collect multiple roots.
     */
    private String extractRootVariableId(Ast.Exp exp) {
        return switch (exp) {
            case Ast.EId id -> {
                // Direct variable reference
                Binding binding = context.lookupLatestBinding(id.name());
                yield binding != null ? binding.id : null;
            }
            case Ast.EInt ignored -> null;
            case Ast.EDouble ignored -> null;
            case Ast.EString ignored -> null;
            case Ast.EBool ignored -> null;
            case Ast.EOpp opp -> {
                // Binary operation - could have multiple dependencies
                // For now, prioritize left side
                String leftId = extractRootVariableId(opp.left());
                yield leftId != null ? leftId : extractRootVariableId(opp.right());
            }
            case Ast.ECall call -> {
                // Function call - depends on return type
                // Could extract from args, but return type is primary
                yield null; // Will handle in ECall propagation
            }
            case Ast.EArray arr -> {
                // Array literal - depends on first element
                if (!arr.elements().isEmpty()) {
                    yield extractRootVariableId(arr.elements().get(0));
                }
                yield null;
            }
            case Ast.EArrayIndex idx -> {
                // Array indexing - depends on array variable
                yield extractRootVariableId(idx.array());
            }
            case Ast.EDInt di -> extractRootVariableId(di.exp());
            case Ast.EStringCast sc -> extractRootVariableId(sc.exp());
            case Ast.ENot not -> extractRootVariableId(not.exp());
            case Ast.EPower pow -> {
                String baseId = extractRootVariableId(pow.base());
                yield baseId != null ? baseId : extractRootVariableId(pow.exponent());
            }
            case Ast.ECmp cmp -> {
                String leftId = extractRootVariableId(cmp.left());
                yield leftId != null ? leftId : extractRootVariableId(cmp.right());
            }
            case Ast.ELogic logic -> {
                String leftId = extractRootVariableId(logic.left());
                yield leftId != null ? leftId : extractRootVariableId(logic.right());
            }
            case Ast.EUnary un -> extractRootVariableId(un.exp());
            case Ast.EAss ass -> {
                // Assignment - depends on the value being assigned
                yield extractRootVariableId(ass.value());
            }
        };
    }
}
