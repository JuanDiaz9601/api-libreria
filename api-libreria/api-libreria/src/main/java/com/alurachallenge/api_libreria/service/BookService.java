package com.alurachallenge.api_libreria.service;


import com.alurachallenge.api_libreria.dto.BookDTO;
import com.alurachallenge.api_libreria.dto.GutendexAuthor;
import com.alurachallenge.api_libreria.dto.GutendexBookResponse;
import com.alurachallenge.api_libreria.model.Author;
import com.alurachallenge.api_libreria.model.Book;
import com.alurachallenge.api_libreria.repository.AuthorRepository;
import com.alurachallenge.api_libreria.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final WebClient gutendexWebClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(WebClient gutendexWebClient, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.gutendexWebClient = gutendexWebClient;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> searchBooks(String query) {
        GutendexBookResponse response = gutendexWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/books/")
                        .queryParam("search", query)
                        .build())
                .retrieve()
                .bodyToMono(GutendexBookResponse.class)
                .block();

        if (response == null || response.results() == null) {
            return List.of();
        }

        return response.results().stream().map(result -> {
            String language = (result.languages() != null && !result.languages().isEmpty())
                    ? result.languages().get(0)
                    : "Unknown";
            int downloadCount = result.download_count();
            String authorsJoined = result.authors().stream()
                    .map(GutendexAuthor::name)
                    .collect(Collectors.joining(", "));

            return new BookDTO(
                    result.title(),
                    authorsJoined,
                    downloadCount,
                    language,
                    result.authors()
            );
        }).collect(Collectors.toList());
    }

    public Book saveBook(BookDTO dto) {
        Book book = new Book(dto.title(), dto.downloadCount(), dto.language());

        // Crear o reutilizar autores
        List<Author> authors = dto.rawAuthors().stream().map(authorDTO -> {
            // Buscamos si existe un autor igual
            Optional<Author> existing = authorRepository.findAll().stream()
                    .filter(a -> a.getName().equals(authorDTO.name()) &&
                            Objects.equals(a.getBirthYear(), authorDTO.birth_year()) &&
                            Objects.equals(a.getDeathYear(), authorDTO.death_year()))
                    .findFirst();
            return existing.orElseGet(() -> {
                Author newAuthor = new Author(authorDTO.name(), authorDTO.birth_year(), authorDTO.death_year());
                return authorRepository.save(newAuthor);
            });
        }).collect(Collectors.toList());

        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    public List<BookDTO> listAllBooks() {
        return bookRepository.findAll().stream()
                .map(b -> new BookDTO(
                        b.getTitle(),
                        b.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")),
                        b.getDownloadCount(),
                        b.getLanguage(),
                        List.of()))
                .collect(Collectors.toList());
    }

    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> listAuthorsAliveInYear(int year) {
        return authorRepository.findAll().stream()
                .filter(a -> a.getBirthYear() != null && a.getBirthYear() <= year)
                .filter(a -> a.getDeathYear() == null || a.getDeathYear() > year)
                .collect(Collectors.toList());
    }

    public List<BookDTO> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language).stream()
                .map(b -> new BookDTO(
                        b.getTitle(),
                        b.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")),
                        b.getDownloadCount(),
                        b.getLanguage(),
                        List.of()))
                .collect(Collectors.toList());
    }
}
