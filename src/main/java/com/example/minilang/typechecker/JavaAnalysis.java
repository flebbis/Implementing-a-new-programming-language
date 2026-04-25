package com.example.minilang.typechecker;

import com.example.minilang.InferenceSuggestion;
import com.example.minilang.TypeError;
import com.example.minilang.TypeReplacementSuggestion;

import java.util.List;

public class JavaAnalysis {
    private List<InferenceSuggestion> inferenceSuggestions;
    private List<TypeError> typeErrors;
    private List<TypeReplacementSuggestion> typeReplacementSuggestions;

    public JavaAnalysis(List<InferenceSuggestion> inferenceSuggestions, List<TypeError> typeErrors,  List<TypeReplacementSuggestion> typeReplacementSuggestions) {
        this.inferenceSuggestions = inferenceSuggestions;
        this.typeErrors = typeErrors;
        this.typeReplacementSuggestions = typeReplacementSuggestions;
    }

    public List<InferenceSuggestion> getInferenceSuggestions() {
        return inferenceSuggestions;
    }

    public List<TypeError> getTypeErrors() {
        return typeErrors;
    }
    public List<TypeReplacementSuggestion> getTypeReplacementSuggestions() {return typeReplacementSuggestions;}
}
