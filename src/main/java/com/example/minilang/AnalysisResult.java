package com.example.minilang;
import java.util.List;
import javax.tools.Diagnostic;

public record AnalysisResult(
        List<Diagnostic> diagnostics,
        List<InferenceSuggestion> inferenceSuggestions) {}
