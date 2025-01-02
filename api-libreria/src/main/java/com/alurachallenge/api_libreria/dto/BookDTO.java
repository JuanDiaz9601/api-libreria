package com.alurachallenge.api_libreria.dto;

import java.util.List;

public record BookDTO(
        String title,
        String authors,
        int downloadCount,
        String language,
        List<GutendexAuthor> rawAuthors
) {}

