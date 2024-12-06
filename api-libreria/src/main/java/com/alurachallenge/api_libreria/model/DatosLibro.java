package com.alurachallenge.api_libreria.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        List<LibroAPI> results
        ) {
}
