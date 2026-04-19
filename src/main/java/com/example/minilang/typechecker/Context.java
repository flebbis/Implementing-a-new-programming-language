package com.example.minilang.typechecker;

import com.example.minilang.Pos;
import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;

import java.util.*;

public class Context {
    // Master registry: all bindings ever created, keyed by unique id
    private final HashMap<String, Binding> bindingRegistry = new HashMap<>();

    // Scope stack: each level maps name -> binding id
    private LinkedList<HashMap<String, String>> scopeStack = new LinkedList<>();

    // Saved scope stack: never popped, so we can look up by scope level later
    private final LinkedList<HashMap<String, String>> savedScopeStack = new LinkedList<>();

    private int scopeLevel = 1;

    public Context() {
        HashMap<String, String> globalScope = new HashMap<>();
        scopeStack.push(globalScope);
        savedScopeStack.push(globalScope);
    }

    /**
     * Create a new binding and push it to the current scope.
     * Returns the binding id.
     */
    public String createBinding(String name, Binding.Kind kind, Pos pos,
                                Ast.Type declaredType, Ast.Type inferredType,
                                boolean explicit) {
        // Check if name already exists in current scope
        if (scopeStack.getFirst().containsKey(name)) {
            throw new TypeException("Duplicate context id " + name);
        }

        // Generate unique id for this binding
        String bindingId = generateBindingId(name);

        // Create the binding
        Binding binding = new Binding(bindingId, name, kind, pos, scopeLevel,
                declaredType, inferredType, explicit);

        // Register it globally
        bindingRegistry.put(bindingId, binding);

        // Add to current scope
        scopeStack.getFirst().put(name, bindingId);

        return bindingId;
    }

    /**
     * Get a binding by id from the global registry.
     */
    public Binding getBinding(String bindingId) {
        return bindingRegistry.get(bindingId);
    }

    /**
     * Lookup a variable name in the scope stack and return its Binding.
     * Returns null if not found.
     */
    public Binding lookupLatestBinding(String name) {
        for (int i = 0; i < scopeStack.size(); i++) {
            HashMap<String, String> scope = scopeStack.get(i);
            if (scope.containsKey(name)) {
                String bindingId = scope.get(name);
                return bindingRegistry.get(bindingId);
            }
        }
        return null;
    }

    /**
     * Lookup a variable name in the scope stack and return its id.
     * Returns null if not found.
     */
    public String lookupLatestBindingId(String name) {
        for (int i = 0; i < scopeStack.size(); i++) {
            HashMap<String, String> scope = scopeStack.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    /**
     * Lookup a variable type (helper for backwards compatibility).
     * Returns null if not found.
     */
    public Ast.Type lookupLatest(String id) {
        Binding binding = lookupLatestBinding(id);
        if (binding != null) {
            return binding.inferredType;
        }
        return null;
    }

    /**
     * Push a new scope.
     */
    public void pushNewScope() {
        scopeLevel++;
        HashMap<String, String> newScope = new HashMap<>();
        scopeStack.push(newScope);
        savedScopeStack.push(newScope);
    }

    /**
     * Pop the current scope.
     */
    public void popScope() {
        scopeLevel--;
        if (scopeStack.size() > 1) {
            scopeStack.removeFirst();
        } else {
            scopeStack.removeFirst();
            scopeStack.push(new HashMap<>());
        }
    }

    /**
     * Update the inferred type of a binding by id.
     */
    public void updateBindingType(String bindingId, Ast.Type newType) {
        Binding binding = bindingRegistry.get(bindingId);
        if (binding == null) {
            throw new TypeException("Cannot update non-existent binding " + bindingId);
        }
        binding.inferredType = newType;
    }

    /**
     * Update the inferred type of a binding by name (for backwards compatibility).
     * Updates the latest in the scope chain.
     */
    public void update(String name, Ast.Type newType) {
        Binding binding = lookupLatestBinding(name);
        if (binding == null) {
            throw new TypeException("Cannot update non-existent variable " + name);
        }
        binding.inferredType = newType;
    }

    /**
     * Add a dependency: this binding depends on another.
     */
    public void addDependency(String bindingId, String dependsOnBindingId) {
        Binding binding = bindingRegistry.get(bindingId);
        Binding dependency = bindingRegistry.get(dependsOnBindingId);

        if (binding == null || dependency == null) {
            return;
        }

        // Add forward direction: binding depends on dependency
        if (!binding.dependencies.contains(dependsOnBindingId)) {
            binding.dependencies.add(dependsOnBindingId);
        }

        // Add backward direction: dependency is depended on by binding
        if (!dependency.dependents.contains(bindingId)) {
            dependency.dependents.add(bindingId);
        }
    }

    /**
     * Get all dependents of a binding (things that depend on this binding).
     */
    public List<String> getDependents(String bindingId) {
        Binding binding = bindingRegistry.get(bindingId);
        if (binding == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(binding.dependents);
    }

    /**
     * Get all dependencies of a binding.
     */
    public List<String> getDependencies(String bindingId) {
        Binding binding = bindingRegistry.get(bindingId);
        if (binding == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(binding.dependencies);
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    /**
     * Look up a binding at a specific absolute scope level (1 = global).
     * Uses savedScopeStack so this works even after the scope has been popped.
     */
    public Binding lookupFromScopeLevel(String name, int level) {
        int index = savedScopeStack.size() - level;
        if (index < 0 || index >= savedScopeStack.size()) {
            return null;
        }
        HashMap<String, String> scope = savedScopeStack.get(index);
        String bindingId = scope.get(name);
        if (bindingId == null) {
            return null;
        }
        return bindingRegistry.get(bindingId);
    }

    /**
     * Look up a binding type at a specific absolute scope level (1 = global).
     * Helper for backwards compatibility.
     */
    public Ast.Type lookupFromScopeLevel(String name, int level, boolean getType) {
        Binding binding = lookupFromScopeLevel(name, level);
        if (binding != null) {
            return binding.inferredType;
        }
        return null;
    }

    /**
     * Get the entire binding registry (for analysis/debugging).
     */
    public HashMap<String, Binding> getBindingRegistry() {
        return new HashMap<>(bindingRegistry);
    }

    /**
     * Generate a unique binding id.
     * Format: name_scopeLevel_uuid
     */
    private String generateBindingId(String name) {
        return name + "_" + scopeLevel + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public void bindExistingToCurrentScope(String name, String bindingId) {
        if (scopeStack.getFirst().containsKey(name)) {
            throw new TypeException("Duplicate context id " + name);
        }
        if (!bindingRegistry.containsKey(bindingId)) {
            throw new TypeException("Unknown binding id " + bindingId + " for " + name);
        }
        scopeStack.getFirst().put(name, bindingId);
    }


    /**
     * Print all bindings in the registry (for testing/debugging).
     */
    public void printBindings() {
        System.err.println("\n=== BINDING REGISTRY ===");
        for (String bindingId : bindingRegistry.keySet()) {
            Binding b = bindingRegistry.get(bindingId);
            System.err.println("  ID: " + bindingId);
            System.err.println("    Name: " + b.name);
            System.err.println("    Kind: " + b.kind);
            System.err.println("    Declared Type: " + TypeConverter.typeToString(b.declaredType));
            System.err.println("    Inferred Type: " + TypeConverter.typeToString(b.inferredType));
            System.err.println("    Explicit: " + b.explicit);
            System.err.println("    Dependencies: " + b.dependencies);
            System.err.println("    Dependents: " + b.dependents);
        }
        System.err.println("=== END BINDINGS ===\n");
    }

}
