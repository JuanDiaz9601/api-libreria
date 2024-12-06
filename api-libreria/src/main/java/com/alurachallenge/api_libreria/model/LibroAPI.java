package com.alurachallenge.api_libreria.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroAPI {
    int id;
    String title;
    List<DatosAuthor> authors;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DatosAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<DatosAuthor> authors) {
        this.authors = authors;
    }


    public String toString(){
        return "id=" + id +
                ", titulo='" + title + '\'' +
                ", Autores=" + getAuthors();
    }

}
