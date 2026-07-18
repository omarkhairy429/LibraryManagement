package com.orange.omar.LibraryManagement.services;

import com.orange.omar.LibraryManagement.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class BookService {
    private Map<String ,Book> books = new HashMap<>();

    public  BookService(Map<String, Book> books) {
        this.books = books;
    }

    public Collection<Book> getBooks() {
        log.info("Getting All Books");
        return books.values();
    }

    public Book getBook(String id) {
        Book book = books.get(id);
        if  (book == null) {
            log.warn("User Input is invalid (Book ID doesn't exist) Can't get");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book nout found");
        }
        log.debug("Book is not null, Getting the Book");
        return book;
    }

    public Book saveBook(Book book) {
        String unique_id = UUID.randomUUID().toString();
        book.setId(unique_id);
        books.put(unique_id, book);
        log.info("Saving the new book");
        return book;
    }

    public Book deleteBook(String id) {
        Book book = books.remove(id);
        if  (book == null) {
            log.warn("User Input is invalid (Book ID doesn't exist) Can't delete");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book nout found");
        }
        log.debug("Book is not null, Deleting the Book");
        return book;
    }

    public Book updateBook(String id, Book updatedBook) {
        Book book  = books.get(id);
        if (book == null) {
            log.warn("User Input is invalid (Book ID doesn't exist) Can't update");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        log.debug("Book is not null, Updating the book");
        updatedBook.setId(id);
        books.put(id, updatedBook);
        return updatedBook;
    }
}
