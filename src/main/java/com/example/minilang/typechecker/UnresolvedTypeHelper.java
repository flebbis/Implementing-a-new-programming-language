package com.example.minilang.typechecker;

import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnresolvedTypeHelper {

    private Context context;
    private String currentFunction;
    private HashMap<String, Signature> functionSignatures;

    public UnresolvedTypeHelper(Context context, String currentFunction, HashMap<String, Signature> functionSignatures) {
        this.context = context;
        this.currentFunction = currentFunction;
        this.functionSignatures = functionSignatures;
    }

    public void setCurrentFunction(String currentFunction) {
        this.currentFunction = currentFunction;
    }

    /**
     * Helper method to check if an expression of unresolved type can satisfy any of the potential conditions.
     * If the expression is not of unresolved type, it is returned as is.
     * If it is of unresolved type, the conditions are added to it and the expression is updated in the context.
     * This allows us to handle cases where we have an expression of unresolved type that can satisfy multiple conditions, such as being used in both a numeric and a string context.
     * @param exp the expression to check for unresolved type and add conditions to if needed
     * @param potentialConditions list of potential conditions that the unresolved expression could satisfy, such as TInt, TDouble...
     * @return
     */
    public Ast.Exp checkUnresolved(Ast.Exp exp, List<Ast.Type> potentialConditions) {
        Ast.Exp result = exp;
        for(Ast.Type condition : potentialConditions) {
            result = addUnresolvedCondition(result, condition);
        }
        return result;
    }


    /** Checks if the condition type satisfies the current conditions of the unresolved type.
     * If so, adds the condition type to the conditions of the unresolved type and updates the context and function signatures accordingly. */
    protected Ast.Exp addUnresolvedCondition(Ast.Exp unresolved, Ast.Type condition) {
        if (unresolved.type() instanceof Ast.TUnresolved unresolvedType) {

            List<Ast.Type> unresolvedConditions = new ArrayList<>(unresolvedType.conditions());
            List<Ast.Type> resolvedConditions = new ArrayList<>();

            if(condition instanceof Ast.TUnresolved unresolvedCondition) {
                // If the condition is itself an unresolved type, we want its conditions
                resolvedConditions.addAll(unresolvedCondition.conditions());
            } else {
                // Otherwise, it's a resolved type and we can add it directly as a condition
                resolvedConditions.add(condition);
            }

            for(Ast.Type resolvedCondition : resolvedConditions) {
                if(!checkUnresolvedConditionsMatch(unresolvedType, resolvedCondition)) {
                    throw new TypeException(unresolvedType.id() + " is of type " + TypeConverter.typeToString(unresolvedType) + ", attempting to use as type " + TypeConverter.typeToString(resolvedCondition), unresolved.pos());
                }
            }

            if(unresolvedConditions.containsAll(resolvedConditions)) {
                return unresolved; // Already satisfies the conditions, no need to update
            }

            unresolvedConditions.addAll(resolvedConditions);

            Ast.Type newType = new Ast.TUnresolved(unresolvedType.id(), unresolvedConditions);

            context.update(unresolvedType.id(), newType, unresolved.pos());
            List<Ast.Type> paramTypes = functionSignatures.get(currentFunction).paramTypes;

            // Update paramtypes in signatures
            for(Ast.Type paramType : paramTypes) {
                if(paramType instanceof Ast.TUnresolved paramUnresolved && paramUnresolved.id().equals(unresolvedType.id())) {
                    int index = paramTypes.indexOf(paramType);
                    functionSignatures.get(currentFunction).paramTypes.set(index, newType);
                }
            }

            return new Ast.EId(unresolvedType.id(), newType, unresolved.pos());
        }
        return unresolved;
    }

    /** Checks if the resolved type satisfies at least one of the conditions of the unresolved type. If the unresolved type has no conditions, it is considered satisfied by any resolved type. */
    protected boolean checkUnresolvedConditionsMatch(Ast.TUnresolved unresolved, Ast.Type resolved) {
        if(unresolved.conditions().isEmpty()) {
            return true; // No conditions to satisfy, return the resolved type
        }

        for (Ast.Type condition : unresolved.conditions()) {
            if (TypeUtils.equalTypes(condition, resolved)) {
                return true; // Condition satisfied, return the resolved type, at least one match
            }
            if(condition instanceof Ast.TInt && resolved instanceof Ast.TDouble) {
                return true; // Allow implicit conversion from int to double, every int is ok as double
            }
        }

        return false; // Were conditions, but the called type did not satisfy any of them
    }
}
