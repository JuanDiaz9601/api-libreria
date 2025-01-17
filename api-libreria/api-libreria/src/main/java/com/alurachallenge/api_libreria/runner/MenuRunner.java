package com.alurachallenge.api_libreria.runner;


import com.alurachallenge.api_libreria.dto.BookDTO;
import com.alurachallenge.api_libreria.model.Author;
import com.alurachallenge.api_libreria.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuRunner implements CommandLineRunner {

    private final BookService bookService;

    public MenuRunner(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("============== MENÚ ==============");
            System.out.println("1 - Buscar libro por titulo");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("6 - Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> {
                    System.out.print("Ingrese el título o palabra clave: ");
                    String query = scanner.nextLine().trim();
                    List<BookDTO> results = bookService.searchBooks(query);
                    if (results.isEmpty()) {
                        System.out.println("No se encontraron libros para la búsqueda: " + query);
                    } else {
                        for (int i = 0; i < results.size(); i++) {
                            BookDTO dto = results.get(i);
                            System.out.printf("%d) Título: %s | Autores: %s | Idioma: %s | Descargas: %d%n",
                                    i+1, dto.title(), dto.authors(), dto.language(), dto.downloadCount());
                        }
                        System.out.print("Seleccione el número del libro a guardar (o 0 para cancelar): ");
                        String selectStr = scanner.nextLine();
                        int select = Integer.parseInt(selectStr);
                        if (select > 0 && select <= results.size()) {
                            BookDTO selectedBook = results.get(select - 1);
                            bookService.saveBook(selectedBook);
                            System.out.println("Libro guardado exitosamente en la base de datos.");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                }
                case "2" -> {
                    List<BookDTO> savedBooks = bookService.listAllBooks();
                    if (savedBooks.isEmpty()) {
                        System.out.println("No hay libros guardados.");
                    } else {
                        savedBooks.forEach(b -> System.out.printf("Título: %s | Autores: %s | Idioma: %s | Descargas: %d%n",
                                b.title(), b.authors(), b.language(), b.downloadCount()));
                    }
                }
                case "3" -> {
                    List<Author> authors = bookService.listAllAuthors();
                    if (authors.isEmpty()) {
                        System.out.println("No hay autores registrados.");
                    } else {
                        authors.forEach(a -> System.out.printf("Autor: %s (nacimiento: %d, muerte: %d)%n",
                                a.getName(),
                                a.getBirthYear() == null ? 0 : a.getBirthYear(),
                                a.getDeathYear() == null ? 9999 : a.getDeathYear()));
                    }
                }
                case "4" -> {
                    System.out.print("Ingrese el año: ");
                    String yearStr = scanner.nextLine();
                    int year = Integer.parseInt(yearStr);
                    List<Author> aliveAuthors = bookService.listAuthorsAliveInYear(year);
                    if (aliveAuthors.isEmpty()) {
                        System.out.println("No hay autores vivos en el año " + year);
                    } else {
                        aliveAuthors.forEach(a -> System.out.printf("Autor: %s (nacimiento: %d, muerte: %s)%n",
                                a.getName(),
                                a.getBirthYear() == null ? 0 : a.getBirthYear(),
                                a.getDeathYear() == null ? "aún vivo" : a.getDeathYear().toString()));
                    }
                }
                case "5" -> {
                    System.out.print("Ingrese el idioma: ");
                    String lang = scanner.nextLine().trim();
                    List<BookDTO> booksByLanguage = bookService.listBooksByLanguage(lang);
                    if (booksByLanguage.isEmpty()) {
                        System.out.println("No hay libros registrados en el idioma " + lang);
                    } else {
                        booksByLanguage.forEach(b -> System.out.printf("Título: %s | Autores: %s | Idioma: %s | Descargas: %d%n",
                                b.title(), b.authors(), b.language(), b.downloadCount()));
                    }
                }
                case "6" -> {
                    System.out.println("Saliendo...");
                    running = false;
                    System.exit(1);

                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

        }

        scanner.close();
    }
}