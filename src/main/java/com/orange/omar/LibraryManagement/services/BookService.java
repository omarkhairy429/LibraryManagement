package com.orange.omar.LibraryManagement.services;

import com.orange.omar.LibraryManagement.models.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BookService {
    private Map<String ,Book> books = new HashMap<>();

    public Collection<Book> getBooks() {
        return books.values();
    }

    public Book getBook(String id) {
        Book book = books.get(id);
        if  (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book nout found");
        }
        return book;
    }

    public Book saveBook(Book book) {
        String unique_id = UUID.randomUUID().toString();
        book.setId(unique_id);
        books.put(unique_id, book);
        return book;
    }

    public Book deleteBook(String id) {
        Book book = books.remove(id);
        if  (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book nout found");
        }
        return book;
    }
}
