package com.alurachallenge.api_libreria.repository;

import com.alurachallenge.api_libreria.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguage(String language);
}
