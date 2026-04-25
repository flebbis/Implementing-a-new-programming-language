package com.example.minilang.typechecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.minilang.InferenceSuggestion;
import com.example.minilang.Pos;
import com.example.minilang.TypeConverter;
import com.example.minilang.TypeReplacementSuggestion;
import com.example.minilang.ast.Ast;

public class StatementTypeChecker {

    private Context context;
    private ExpressionTypeChecker expressionTypeChecker;
    private String currentFunction;
    private HashMap<String, Signature> functionSignatures;
    private Context inferenceContext; // For tracking variable types during inference phase
    private List<InferenceSuggestion> inferenceSuggestions; // For collecting suggestions during inference phase
    private List<String> calledFunctions = new ArrayList<>(); // For tracking called functions during inference phase
    private UnresolvedTypeHelper unresolvedTypeHelper;
    private List<TypeReplacementSuggestion>  typeReplacementSuggestions;

    public StatementTypeChecker(Context context, HashMap<String, Signature> functionSignatures,
            Context inferenceContext, List<InferenceSuggestion> inferenceSuggestions, List<TypeReplacementSuggestion>  typeReplacementSuggestions) {
        this.context = context;
        this.functionSignatures = functionSignatures;
        this.inferenceContext = inferenceContext;
        this.inferenceSuggestions = inferenceSuggestions;
        this.unresolvedTypeHelper = new UnresolvedTypeHelper(context, currentFunction, functionSignatures);
        this.expressionTypeChecker = new ExpressionTypeChecker(context, functionSignatures, unresolvedTypeHelper);
        this.typeReplacementSuggestions = typeReplacementSuggestions;
    }

    public List<String> getCalledFunctions() {
        return expressionTypeChecker.getCalledFunctions();
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
        // We look up if the variable exists in the current scope chain.
        Ast.Type existingType = context.lookupLatest(sInit.name());

        // If the variable exists and this is an implicit statement (e.g. "x = 5" not "int x = 5"),
        // treat it as an Assignment to the existing variable.
        if (existingType != null && typeToCheck instanceof Ast.TUnknown) {
            // Transform to an expression
            Ast.EAss assign = new Ast.EAss(sInit.name(), value, Ast.AssOp.ASSIGN, existingType, sInit.pos());
            return new Ast.SExp(expressionTypeChecker.typeCheck(assign), sInit.pos());
        }

        // If we reach here, it is a new Declaration.

        // Infer type if unknown
        if (typeToCheck instanceof Ast.TUnknown) {
            if (!(value.type() instanceof Ast.TUnknown)) {
                typeToCheck = value.type();

                if (typeToCheck instanceof Ast.TArray) {
                    inferenceCheckArray((Ast.TArray) typeToCheck, sInit.name(), sInit.pos());
                } else {
                    InferenceSuggestion inferenceSuggestion = new InferenceSuggestion(
                            sInit.name(),
                            TypeConverter.typeToString(typeToCheck),
                            sInit.pos().line,
                            sInit.pos().column,
                            sInit.pos().line,
                            sInit.pos().column + sInit.name().length(),
                            "Suggestion: " + TypeConverter.typeToString(typeToCheck) + " " + sInit.name());

                    // Avoid duplicates for multiple inference passes
                    if (!inferenceSuggestions.contains(inferenceSuggestion)) {
                        inferenceSuggestions.add(inferenceSuggestion);
                    }
                }
            }
        }

        // Verify types match for explicit declarations
        // Allow TUnknown values to bypass checks during inference pass
        if (typeToCheck instanceof Ast.TArray) {
            typeToCheck = checkTypeCompatabilityArrays((Ast.TArray) typeToCheck, value);
        } else {
            value = checkTypeCompatabilityNonArrays(typeToCheck, value, sInit);

        }

        context.pushToCurrentScope(sInit.name(), typeToCheck, sInit.pos());
        return new Ast.SInit(typeToCheck, sInit.name(), value, sInit.pos());
    }
    
    private Ast.Type checkTypeCompatabilityArrays(Ast.TArray sInitType, Ast.Exp value) {
        if (value.type() instanceof Ast.TArray) {
            Ast.TArray valueType = (Ast.TArray) value.type();


            // Element type is unkown, probably no elements yet, just pass, is okey
            if (valueType.elementType() instanceof Ast.TUnknown) {

            } else if(sInitType.elementType() instanceof Ast.TUnknown) {

            } else {
                // If both are known, they must match
                if (!TypeUtils.equalTypes(sInitType.elementType(), valueType.elementType())) {
                    throw new TypeException(
                            "Type mismatch:", TypeConverter.typeToString(sInitType.elementType()),
                                    TypeConverter.typeToString(valueType.elementType()),
                            value.pos());
                } else {
                    // probably because of mismatch in size
                    if(!sInitType.equals(valueType)) {
                        return valueType; // if they dont match in size, value will be correct
                    }
                }
            }
        } else {
            throw new TypeException("Attempting to assign non array value to array", value.pos());
        }
        return sInitType;
    }
    
    private Ast.Exp checkTypeCompatabilityNonArrays(Ast.Type sInitType, Ast.Exp value,  Ast.SInit sInit) {
        if (!compareTypes(sInitType, value.type()) && !(value.type() instanceof Ast.TUnknown)) {
            if (sInitType instanceof Ast.TDouble && value.type() instanceof Ast.TInt) {
                value = new Ast.EDInt(value, new Ast.TDouble(), value.pos());
                return value;
            } else {
                typeReplacementSuggestions.add(new TypeReplacementSuggestion(
                        sInit.name(),
                        TypeConverter.typeToString(sInitType),
                        sInit.pos().line,
                        sInit.pos().column,
                        sInit.pos().line,
                        sInit.pos().column - sInitType.toString().length(),
                        TypeConverter.typeToString(value.type())
                ));
                throw new TypeException(
                        "Type mismatch:", TypeConverter.typeToString(sInitType),
                        TypeConverter.typeToString(value.type()),
                        value.pos());
            }
        }
        return value;
    }
    
    private void inferenceCheckArray(Ast.TArray type, String name, Pos pos) {
        if (TypeUtils.arrayIsUnkown(type)) {
            if (inferenceContext != null) {
                Ast.Type inferred = inferenceContext.lookupFromScopeLevel(name, context.getScopeLevel());
                if (inferred != null && inferred instanceof Ast.TArray) {
                    type =  (Ast.TArray) inferred;
                }
            }
        }

        // If not unkown now, we can make inference suggestion
        if (!TypeUtils.arrayIsUnkown(type)) {
            InferenceSuggestion inferenceSuggestion = new InferenceSuggestion(
                    name,
                    TypeConverter.typeToString(type),
                    pos.line,
                    pos.column,
                    pos.line,
                    pos.column + name.length(),
                    "Suggestion: " + TypeConverter.typeToString(type) + " " + name);

            // Avoid duplicates for multiple inference passes
            if (!inferenceSuggestions.contains(inferenceSuggestion)) {
                inferenceSuggestions.add(inferenceSuggestion);
            }
        }
    }

    private boolean compareTypes(Ast.Type declaredType, Ast.Type valueType) {
        return TypeUtils.equalTypes(declaredType, valueType);
    }


    public Ast.SExp typeCheck(Ast.SExp stmt) {
        Ast.Exp exp = expressionTypeChecker.typeCheck(stmt.exp());
        return new Ast.SExp(exp, stmt.pos());
    }

    public Ast.Stmt typeCheck(Ast.SDecl sDecl) {
        Ast.Type type = new Ast.TUnknown();

        if(sDecl.type() instanceof Ast.TUnknown) {
            // If unknown, try checking the inference context for the type
            int scopeLvl = context.getScopeLevel();

            if (inferenceContext.lookupFromScopeLevel(sDecl.name(), scopeLvl) != null && !(inferenceContext.lookupFromScopeLevel(sDecl.name(), scopeLvl) instanceof Ast.TUnknown)) {
                type = inferenceContext.lookupFromScopeLevel(sDecl.name(), scopeLvl);
                // Add to suggestions for language server
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
        context.pushToCurrentScope(sDecl.name(), type, sDecl.pos());
        return new Ast.SDecl(type, sDecl.name(), sDecl.pos());
    }

    public Ast.Stmt typeCheck(Ast.SWhile stmt) {
        context.pushNewScope();
        Ast.Exp condition = expressionTypeChecker.typeCheck(stmt.condition());

        condition = unresolvedTypeHelper.checkUnresolved(condition, List.of(new Ast.TBool()));

        if (!(TypeUtils.equalTypes(condition.type(), new Ast.TBool()))) {
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

        exp = unresolvedTypeHelper.checkUnresolved(exp, List.of(new Ast.TInt()));

        if (!(TypeUtils.equalTypes(exp.type(), new Ast.TInt()))) {
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
        if (isUnkownType(signature.returnType) && !isUnkownType(value.type())) {
            signature.setReturnType(value.type());
            signature.isInference = true;
        } else {
            signature.isInference = false;
        }

        value = unresolvedTypeHelper.checkUnresolved(value, List.of(signature.returnType));

        if (!TypeUtils.equalTypes(signature.returnType, value.type())) {
            // Allow implicit conversion from int to double
            if (signature.returnType instanceof Ast.TDouble && value.type() instanceof Ast.TInt) {
                value = new Ast.EDInt(value, new Ast.TDouble(), value.pos());
            } else {
                throw new TypeException("Incorrect return type in function '" + currentFunction + "'",
                         TypeConverter.typeToString(signature.returnType), TypeConverter.typeToString(value.type()), stmt.pos());
            }
        }

        return new Ast.SReturn(value, stmt.pos());
    }

    private boolean isUnkownType(Ast.Type type) {
        if (type instanceof Ast.TUnknown) {
            return true;
        } else if (type instanceof Ast.TArray) {
            Ast.TArray tArray = (Ast.TArray) type;
            if (tArray.elementType() instanceof Ast.TUnknown) {
                return true; // unkown array type
            }
        }
        return false;
    }

    public Ast.Stmt typeCheck(Ast.SIf stmt) {
        Ast.Exp condition = expressionTypeChecker.typeCheck(stmt.condition());

        condition = unresolvedTypeHelper.checkUnresolved(condition, List.of(new Ast.TBool()));

        if (!TypeUtils.equalTypes(condition.type(), new Ast.TBool())) {
            throw new TypeException("Condition of if statement must be of type bool", stmt.condition().pos());
        }

        // Check then branch
        if (!(stmt.thenBranch() instanceof Ast.SBlock)) {
            throw new TypeException("Then branch of if statement must be a block statement", stmt.thenBranch().pos());
        }

        context.pushNewScope();
        Ast.Stmt thenStmt = typeCheck(stmt.thenBranch()); // Check for any type errors in the then branch before
                                                          // checking the else branch (for better error messages)
        context.popScope();

        Ast.Stmt elseStmt = null;
        if(stmt.elseBranch() != null) {
            if (!(stmt.elseBranch() instanceof Ast.SBlock || stmt.elseBranch() instanceof Ast.SIf)) {
                throw new TypeException("Else branch of if statement must be a block statement",
                        stmt.elseBranch().pos());
            }
            context.pushNewScope();
            elseStmt = typeCheck(stmt.elseBranch());
            context.popScope();
        }

        return new Ast.SIf(condition, thenStmt, elseStmt, stmt.pos()); // Placeholder
    }

    public Ast.SBlock typeCheck(Ast.SBlock stmt) {
        context.pushNewScope();
        List<Ast.Stmt> checkedStmts = new ArrayList<>();
        for(Ast.Stmt s : stmt.statements()) {
            checkedStmts.add(typeCheck(s));
        }
        context.popScope();

        return new Ast.SBlock(checkedStmts, stmt.pos());
    }

    public void setCurrentFunction(String currentFunction) {
        this.currentFunction = currentFunction;
        expressionTypeChecker.setCurrentFunction(currentFunction);
    }

    public void updateInferenceContext(Context context) {
        inferenceContext = context;
    }
}
