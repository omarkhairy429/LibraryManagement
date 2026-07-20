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

    // To Track Number of borrowed books
    private Map<String, Integer> numberOfBorrowedBooks = new HashMap<>();


    public BorrowRecordService(BookService bookService) {
        this.bookService = bookService;
    }

   public BorrowRecord borrowBook(BorrowRecord record) {
        String unique_id = UUID.randomUUID().toString();
        record.setBorrowID(unique_id);
        Book book = bookService.getBook(record.getBookID());
        numberOfBorrowedBooks.put(record.getUserID(), numberOfBorrowedBooks.getOrDefault(record.getUserID(), 0));
        System.out.println("Number of borrowed books = " + numberOfBorrowedBooks.get(record.getUserID()));
        /* Check if the book is not currently borrowed */
       if (!isAvailable(record.getBookID())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }

       /* Check if the user has fewer than 5 active borrowed books */
       if (!isLessThanFiveBooks(record.getUserID())) {
           throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has 5 books borrowed");
       }

       /* Check if the user has 3 or more overdue books */
       if (hasOverdueBooks(record.getUserID())) {
           throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has 3 or more overdue books");
       }


        record.setStatus("Borrowed");
        Date now = new Date();
        record.setBorrowDate(now);
        numberOfBorrowedBooks.put(record.getUserID(), numberOfBorrowedBooks.get(record.getUserID()) + 1);
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

   private boolean isLessThanFiveBooks(String userID) {
        if (numberOfBorrowedBooks.get(userID) >= 5) {
            return false;
        }
        return true;
   }

    private boolean hasOverdueBooks(String userID) {
        int overdueCount = 0;
        Date currentDate = new Date();

        for (BorrowRecord record : records.values()) {
            boolean isSameUser = userID.equals(record.getUserID());
            boolean isNotReturned = !"Returned".equalsIgnoreCase(record.getStatus());
            boolean isOverdue = currentDate.after(record.getDueDate());

            if (isSameUser && isNotReturned && isOverdue) {
                overdueCount++;
                if (overdueCount >= 3) {
                    return true;
                }
            }
        }

        return false;
    }
}
