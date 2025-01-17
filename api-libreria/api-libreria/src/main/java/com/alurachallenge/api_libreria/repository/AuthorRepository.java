package com.alurachallenge.api_libreria.repository;

import com.alurachallenge.api_libreria.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
