package com.orange.omar.LibraryManagement.services;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.models.BorrowRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BorrowRecordService {
    private BookService bookService;
    private Map<String , BorrowRecord> records = new HashMap<>();


    public BorrowRecordService(BookService bookService) {
        this.bookService = bookService;
    }

   public BorrowRecord borrowBook(BorrowRecord record) {
        String unique_id = UUID.randomUUID().toString();
        record.setBorrowID(unique_id);
        Book book = bookService.getBook(record.getBookID());

        /* Check if the book is not currently borrowed */
       if (!isAvailable(record.getBookID())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }

       /* Check if the user has fewer than 5 active borrowed books */



        book.setAvailable(false);
        bookService.updateBook(record.getBookID(), book);
        records.put(record.getBorrowID(), record);
        return record;
    }

    private boolean isAvailable(String bookID) {
        Book book = bookService.getBook(bookID);
        if (!book.isAvailable()) {
            return false;
        }
        return true;
   }
}
