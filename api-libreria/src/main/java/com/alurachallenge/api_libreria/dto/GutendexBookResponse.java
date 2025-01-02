package com.alurachallenge.api_libreria.dto;

import java.util.List;

public record GutendexBookResponse(
        int count,
        String next,
        String previous,
        List<GutendexResult> results
) {
    public List<GutendexResult> getResults() {
        return results;
    }
}
