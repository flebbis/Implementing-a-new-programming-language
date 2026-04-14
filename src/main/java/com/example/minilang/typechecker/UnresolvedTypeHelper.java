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

    /** Adds numeric conditions (int and double) to an unresolved expression, if it is not already resolved to a numeric type. */
    protected Ast.Exp addNumericConditionToUnresolvedExp(Ast.Exp unresolved) {
        Ast.Exp addedInt = addUnresolvedCondition(unresolved, new Ast.TInt());
        return addUnresolvedCondition(addedInt, new Ast.TDouble());
    }

    /** Checks if the condition type satisfies the current conditions of the unresolved type.
     * If so, adds the condition type to the conditions of the unresolved type and updates the context and function signatures accordingly. */
    protected Ast.Exp addUnresolvedCondition(Ast.Exp unresolved, Ast.Type condition) {
        if (unresolved.type() instanceof Ast.TUnresolved unresolvedType) {

            System.out.println("getting unresolved " + unresolved.type() + " with conditions " + unresolvedType.conditions() + " and adding condition " + condition);
            if(!checkUnresolvedConditionsMatch(unresolvedType, condition)) {
                throw new TypeException(unresolvedType.id() + " is of type " + TypeConverter.typeToString(unresolvedType) + ", attempting to use as type " + TypeConverter.typeToString(condition), unresolved.pos());
            }

            List<Ast.Type> conditions = new ArrayList<>(unresolvedType.conditions());
            if(conditions.contains(condition)) {
                return unresolved; // Already satisfies the conditions, no need to update
            }
            conditions.add(condition);

            Ast.Type newType = new Ast.TUnresolved(unresolvedType.id(), conditions);
            context.update(unresolvedType.id(), newType);
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
