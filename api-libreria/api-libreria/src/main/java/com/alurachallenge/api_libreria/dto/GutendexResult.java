package com.alurachallenge.api_libreria.dto;

import java.util.List;
import java.util.Map;

public record GutendexResult(
        int id,
        String title,
        List<GutendexAuthor> authors,
        Map<String,String> formats,
        List<String> languages,
        int download_count
) {}

