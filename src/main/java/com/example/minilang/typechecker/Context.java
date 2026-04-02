package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.HashMap;
import java.util.LinkedList;

public class Context {
    HashMap<String, Ast.Type> contextMap = new HashMap<>();
    LinkedList<HashMap<String, Ast.Type>> contextStack = new LinkedList<>();
    /** Same HashMap references as contextStack, but never popped — so scopes survive after popScope() */
    private final LinkedList<HashMap<String, Ast.Type>> savedContextStack = new LinkedList<>();
    private int scopeLevel = 1;

    public Context() {
        contextStack.push(contextMap); // global context
        savedContextStack.push(contextMap); // same reference
    }

    /** Push a new id-type pair onto the stack, using the latest scope */
    public void pushToCurrentScope(String id, Ast.Type type) {
        if(contextStack.getFirst().containsKey(id)) {
            throw new TypeException("Duplicate context id " + id);
        } else {
            contextStack.getFirst().put(id, type);
        }
    }

    public void pushNewScope() {
        scopeLevel++;
        HashMap<String, Ast.Type> newScope = new HashMap<>();
        contextStack.push(newScope);
        savedContextStack.push(newScope); // save the same reference — never removed
    }

    /** Lookup the variable in the context */
    public Ast.Type lookupLatest(String id) {
        for(int i = 0; i < contextStack.size(); i++) {
            if (contextStack.get(i).containsKey(id)) {
                return contextStack.get(i).get(id);
            }
        }
        return null;
    }

    /** Remove the latest environment, if not the global scope */
    public void popScope() {
        scopeLevel--;
        if(contextStack.size() > 1) {
            contextStack.removeFirst();
        } else {
            contextStack.removeFirst();
            contextStack.push(new HashMap<>()); // reset global scope to empty
        }
    }

    /** Update the type of an existing variable in the context stack */
    public void update(String id, Ast.Type newType) {
        for(int i = 0; i < contextStack.size(); i++) {
            if (contextStack.get(i).containsKey(id)) {
                contextStack.get(i).put(id, newType);
                return;
            }
        }
        throw new TypeException("Cannot update non-existent variable " + id);
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    /**
     * Look up a variable at a specific absolute scope level (1 = global).
     * Uses savedContextStack so this works even after the scope has been popped.
     */
    public Ast.Type lookupFromScopeLevel(String id, int level) {
        // savedContextStack index: index 0 is the most recently pushed scope,
        // so scope level 'n' lives at index (savedContextStack.size() - n).
        int index = savedContextStack.size() - level;
        if (index < 0 || index >= savedContextStack.size()) {
            return null;
        }
        return savedContextStack.get(index).get(id); // returns null if not present
    }

}
