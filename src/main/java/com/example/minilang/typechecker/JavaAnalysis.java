package com.example.minilang.typechecker;

import com.example.minilang.InferenceSuggestion;
import com.example.minilang.TypeError;

import java.util.List;

public class JavaAnalysis {
    private List<InferenceSuggestion> inferenceSuggestions;
    private List<TypeError> typeErrors;

    public JavaAnalysis(List<InferenceSuggestion> inferenceSuggestions, List<TypeError> typeErrors) {
        this.inferenceSuggestions = inferenceSuggestions;
        this.typeErrors = typeErrors;
    }

    public List<InferenceSuggestion> getInferenceSuggestions() {
        return inferenceSuggestions;
    }

    public List<TypeError> getTypeErrors() {
        return typeErrors;
    }
}
