package com.example.minilang.typechecker;

import com.example.minilang.Pos;
import com.example.minilang.ast.Ast;

import java.util.*;

public class Context {
    HashMap<String, Ast.Type> contextMap = new HashMap<>();
    LinkedList<HashMap<String, Ast.Type>> contextStack = new LinkedList<>();
    /** Same HashMap references as contextStack, but never popped — so scopes survive after popScope() */
    private final LinkedList<HashMap<String, Ast.Type>> savedContextStack = new LinkedList<>();
    private int scopeLevel = 1;
    private final HashMap<String, Binding> bindingRegistry = new HashMap<>();

    public Context() {
        contextStack.push(contextMap); // global context
        savedContextStack.push(contextMap); // same reference
    }

    /** Push a new id-type pair onto the stack, using the latest scope */
    public void pushToCurrentScope(String id, Ast.Type type, Pos pos) {

        if(IllegalIDs.illegalIDs.contains(id)) {
            throw new TypeException("Illegal identifier '" + id + "'", pos);
        }

        if(contextStack.getFirst().containsKey(id)) {
            throw new TypeException("Duplicate context id " + id, pos);
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

    public boolean isInCurrentScope(String id) {
        return contextStack.getFirst().containsKey(id);
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
    public void update(String id, Ast.Type newType, Pos pos) {
        for(int i = 0; i < contextStack.size(); i++) {
            if (contextStack.get(i).containsKey(id)) {
                contextStack.get(i).put(id, newType);
                return;
            }
        }
        throw new TypeException("Cannot update non-existent variable " + id, pos);
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

    public List<String> getDependentsRecursive(String bindingId) {
        List<String> result = new ArrayList<>();
        Deque<String> work = new ArrayDeque<>();
        Set<String> seen = new HashSet<>();

        work.push(bindingId);

        while (!work.isEmpty()) {
            String current = work.pop();
            for (String dependentId : getDependents(current)) {
                if (seen.add(dependentId)) {
                    result.add(dependentId);
                    work.push(dependentId);
                }
            }
        }

        return result;
    }

    public void cascadeTypeChange(String bindingId, Ast.Type newType) {
        Binding root = getBinding(bindingId);
        if (root == null) return;

        root.inferredType = newType;

        for (String dependentId : getDependentsRecursive(bindingId)) {
            Binding dep = getBinding(dependentId);
            if (dep != null) {
                dep.inferredType = newType;
            }
        }
    }
    public Binding getBinding(String bindingId) {
        return bindingRegistry.get(bindingId);
    }

    public List<String> getDependents(String bindingId) {
        Binding binding = bindingRegistry.get(bindingId);
        if (binding == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(binding.dependents);
    }

    public List<String> getDependencies(String bindingId) {
        Binding binding = bindingRegistry.get(bindingId);
        if (binding == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(binding.dependencies);
    }
    public HashMap<String, Binding> getBindingRegistry() {
        return new HashMap<>(bindingRegistry);
    }
    public Binding createBinding(String name, Binding.Kind kind, Pos pos,
                                 Ast.Type declaredType, Ast.Type inferredType,
                                 boolean explicit) {
        if (contextStack.getFirst().containsKey(name)) {
            throw new TypeException("Duplicate context id " + name, pos);
        }

        String bindingId = name + "_" + scopeLevel + "_" + bindingRegistry.size();

        Binding binding = new Binding(bindingId, name, kind, pos, scopeLevel,
                declaredType, inferredType, explicit);

        bindingRegistry.put(bindingId, binding);
        contextStack.getFirst().put(name, inferredType);
        return binding;
    }
    public void addDependency(String bindingId, String dependsOnBindingId) {
        Binding binding = bindingRegistry.get(bindingId);
        Binding dependency = bindingRegistry.get(dependsOnBindingId);

        if (binding == null || dependency == null) return;

        if (!binding.dependencies.contains(dependsOnBindingId)) {
            binding.dependencies.add(dependsOnBindingId);
        }
        if (!dependency.dependents.contains(bindingId)) {
            dependency.dependents.add(bindingId);
        }
    }


}
