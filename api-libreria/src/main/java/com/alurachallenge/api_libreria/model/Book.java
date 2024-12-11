package com.alurachallenge.api_libreria.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private List<Author> authors = new ArrayList<>();

    private String language;
    private int downloadCount;

    public Book() {}

    public Book(String title, int downloadCount, String language) {
        this.title = title;
        this.downloadCount = downloadCount;
        this.language = language;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public List<Author> getAuthors() { return authors; }
    public String getLanguage() { return language; }
    public int getDownloadCount() { return downloadCount; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }
    public void setLanguage(String language) { this.language = language; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }
}

