package com.example.minilang.typechecker;

import com.example.minilang.InferenceSuggestion;
import com.example.minilang.TypeError;
import com.example.minilang.TypeReplacementSuggestion;

import java.util.List;
import java.util.Map;

public class JavaAnalysis {
    private List<InferenceSuggestion> inferenceSuggestions;
    private List<TypeError> typeErrors;
    private List<TypeReplacementSuggestion> typeReplacementSuggestions;
    private Map<String, Binding> bindings;

    public JavaAnalysis(List<InferenceSuggestion> inferenceSuggestions, List<TypeError> typeErrors,
                        List<TypeReplacementSuggestion> typeReplacementSuggestions,
                        Map<String, Binding> bindings) {
        this.inferenceSuggestions = inferenceSuggestions;
        this.typeErrors = typeErrors;
        this.typeReplacementSuggestions = typeReplacementSuggestions;
        this.bindings = bindings;
    }

    public Map<String, Binding> getBindings() {
        return bindings;
    }
    public List<InferenceSuggestion> getInferenceSuggestions() {
        return inferenceSuggestions;
    }

    public List<TypeError> getTypeErrors() {
        return typeErrors;
    }
    public List<TypeReplacementSuggestion> getTypeReplacementSuggestions() {return typeReplacementSuggestions;}
}
