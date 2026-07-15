package com.orange.omar.LibraryManagement.controllers;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.services.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Collection<Book> get() {
        return bookService.getBooks();
    }

    @GetMapping("/books/{id}")
    public Book  getByID(@PathVariable String id) {
        return bookService.getBook(id);
    }

    @PostMapping("/books")
    public Book post(@RequestBody @Valid Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/books/{id}")
    public Book post(@PathVariable String id) {
        return bookService.deleteBook(id);
    }
}
