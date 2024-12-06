package com.alurachallenge.api_libreria.principal;

import com.alurachallenge.api_libreria.model.DatosAuthor;
import com.alurachallenge.api_libreria.model.DatosLibro;
import com.alurachallenge.api_libreria.service.ConsumoAPI;
import com.alurachallenge.api_libreria.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.stream.Collectors;


public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private Menu menu = new Menu();


    public DatosLibro getDatosLibro() throws JsonProcessingException {
        menu.mostrar();
        
        var json = consumoApi.obtenerDatos("https://gutendex.com/books/");

        ConvierteDatos convierteDatos = new ConvierteDatos();
        var datos = convierteDatos.obtenerDatos(json, DatosLibro.class);
        System.out.println(datos);
        var librosFiltrados = datos.results().stream().filter(libro -> "Frankenstein; Or, The Modern Prometheus".equalsIgnoreCase(libro.getTitle()))
                .collect(Collectors.toList());
        System.out.println(librosFiltrados);


        return datos;
    };





}
