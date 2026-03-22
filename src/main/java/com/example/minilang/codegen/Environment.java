package com.example.minilang.codegen;

import com.example.minilang.ast.Ast;

import java.util.HashMap;
import java.util.LinkedList;

public class Environment {
    private HashMap<String, String> variableRegisterMap = new HashMap<>();
    private LinkedList<HashMap<String, String>> scope = new LinkedList<>();

    public Environment() {
        scope.push(variableRegisterMap); // global scope
    }

    public void pushToCurrentScope(String id, String register) {
        if(scope.getFirst().containsKey(id)) {
            throw new RuntimeException("Duplicate variable declaration: " + id);
        } else {
            scope.getFirst().put(id, register);
        }
    }

    /**
     * Push a new scope onto the environment stack.
     * This is typically called when entering a new block (e.g., function body, if statement, etc.).
     */
    public void pushNewScope() {
        scope.push(new HashMap<>());
    }

    /**
     * Lookup the variable in the environment, starting from the latest scope and moving outward.
     * @param id The variable name to look up.
     * @return The register associated with the variable, if found. Else throws a RuntimeException.
     */
    public String lookup(String id) {
        for(int i = 0; i < scope.size(); i++) {
            if (scope.get(i).containsKey(id)) {
                return scope.get(i).get(id);
            }
        }
        return null;
    }


    public boolean existsInCurrentScope(String id) {
        return scope.getFirst().containsKey(id);
    }

    /**
     * Should only be applied to functions
     * @return true if we're in the base scope (i.e., the function's own scope), false if we're in a nested scope (e.g., inside an if statement or while loop within the function)
     */
    public boolean isInBaseScope() {
        return scope.size() == 2;
    }

    public void popScope() {
        if(scope.size() > 1) {
            scope.removeFirst();
        } else {
            throw new RuntimeException("Cannot pop global scope");
        }
    }

}
