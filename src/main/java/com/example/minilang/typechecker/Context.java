package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.HashMap;
import java.util.LinkedList;

public class Context {
    HashMap<String, Ast.Type> contextMap = new HashMap<>();
    LinkedList<HashMap<String, Ast.Type>> contextStack = new LinkedList<>();

    public Context() {
        contextStack.push(contextMap); // global context
    }

    /** Push a new id-type pair onto the stack, using the latest scope */
    public void pushToCurrentScope(String id, Ast.Type type) {
        if(contextStack.getFirst().containsKey(id)) {
            throw new TypeException("Duplicate context id " + id);
        } else {
//            if(type == Ast.Type.TUnknown) {
//                throw new TypeException("Cannot declare variable of type void");
//            }
            contextStack.getFirst().put(id, type);
        }

    }

    public void pushNewScope() {
        contextStack.push(new HashMap<String, Ast.Type>());
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
        if(contextStack.size() > 1) {
            contextStack.removeFirst();
        } else {
            throw new TypeException("Cannot pop global scope");
        }
    }

}
