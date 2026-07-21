package com.orange.omar.LibraryManagement.steps;




import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.models.BorrowRecord;
import com.orange.omar.LibraryManagement.services.BookService;
import com.orange.omar.LibraryManagement.services.BorrowRecordService;
import com.sun.source.tree.LambdaExpressionTree;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowBookSteps {
    @Autowired
    private BorrowRecordService borrowRecordService;
    @Autowired
    private BookService bookService;
    private Book book;
    private BorrowRecord borrowRecord;
    private String userID;
    private boolean bookLimitExceded;
    private List<Book>  books= new ArrayList();
    private String borrowID;

    @Given("I have a book in my database")
    public void havingBookInDatabase() {
        userID = "Omar123";
        book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Omar Fayed");
        bookService.saveBook(book);
    }

    @When("I try to borrow a book")
    public void borrowingBook() {
        borrowRecord = new BorrowRecord();
        borrowRecord.setBookID(book.getId());
        borrowRecord.setUserID(userID);
        Date dueDate = new Date(System.currentTimeMillis() + 100000000L);
        borrowRecord.setDueDate(dueDate);
    }

    @When("I have < {int} borrowed books")
    public void havingLessThanFiveBorrowed(Integer borrowLimit) {

    }

    @When("I don't have >= {int} overdue books")
    public void havingLessThanThreeOverdue(Integer overdueLimit) {

    }

    @Then("New record should be created")
    public void creatingNewRecord() {
        BorrowRecord borrowRecord1 = borrowRecordService.borrowBook(borrowRecord);
        assertNotNull(borrowRecord1);
    }

    @Then("Ensure book is marked as Borrowed")
    public void EnsureBorrowed() {
        assertEquals("Borrowed", borrowRecord.getStatus());
    }



    @Given("I have {int} books stored in the database")
    public void havingSixBooksInDatabase(Integer numberOfBooks) {
        userID = "Omar123456";
        for (int i = 0; i < numberOfBooks; i++) {
            book = new Book();
            book.setId(UUID.randomUUID().toString());
            book.setTitle("Book " + (i + 1));
            book.setAuthor("Author " + (i + 1));
            book.setAvailable(true);
            bookService.saveBook(book);
            books.add(book);
        }
    }

    @When("I borrow more than five books")
    public void borrowingMoreThanFiveBooks() {
        for (int i = 0; i < 5; i++) {
            BorrowRecord record = new BorrowRecord();
            record.setUserID(userID);
            record.setBookID(books.get(i).getId());
            borrowRecordService.borrowBook(record);
        }

        /* Borrow the 6th book */
        BorrowRecord sixthRecord = new BorrowRecord();
        sixthRecord.setUserID(userID);
        sixthRecord.setBookID(books.get(5).getId());

        bookLimitExceded = false;

        try {
            borrowRecordService.borrowBook(sixthRecord);
        } catch (Exception e) {
            bookLimitExceded = true;
        }

    }

    @Then("I should get an exception can't borrow more than 5")
    public void checkGettingException() {
        assertTrue(bookLimitExceded);
    }


    @Given("I have {int} books stored in my database")
    public void havingFourBooksStored(Integer numberOfBooks){
        userID = "Omar12345678";
        books.clear();
        for (int i = 0; i < numberOfBooks; i++) {
            Book book = new Book();
            book.setId(UUID.randomUUID().toString());
            book.setTitle("Book " + (i + 1));
            book.setAuthor("Author");
            book.setAvailable(true);
            bookService.saveBook(book);
            books.add(book);
        }
    }

    @When("I have {int} overdue books")
    public void havingThreeOverdueBooks(Integer numberOfOverdues) {
        for (int i = 0; i < numberOfOverdues; i++) {
            BorrowRecord record = new BorrowRecord();
            record.setUserID(userID);
            record.setBookID(books.get(i).getId());

            borrowRecordService.borrowBook(record);


            Date pastDate = new Date(System.currentTimeMillis() - 1000000L);
            record.setDueDate(pastDate);
        }
    }

    @When("I try to borrow one more book")
    public void TryingToBorrowOneMoreBook() {
        BorrowRecord newRecord = new BorrowRecord();
        newRecord.setUserID(userID);
        newRecord.setBookID(books.get(3).getId());
        bookLimitExceded = false;

        try {
            borrowRecordService.borrowBook(newRecord);
        } catch (Exception e) {
            bookLimitExceded = true;
        }
    }

    @Then("I should get an exception You are blocked")
    public void checkGettingAnException() {
        assertTrue(bookLimitExceded);
    }

    @Given("There is a book stored in database")
    public void havingABookStored() {
        book = new Book();
        book.setTitle("Book");
        book.setAuthor("Omar");
        book.setAvailable(true);
        bookService.saveBook(book);
    }

    @When("I borrow the book")
    public void borrowingTheBook() {
        BorrowRecord record = new BorrowRecord();
        record.setUserID("USER123");
        record.setBookID(book.getId());

        BorrowRecord borrowedRecord = borrowRecordService.borrowBook(record);
        borrowID = borrowedRecord.getBorrowID();
    }

    @When("I try to return it")
    public void returningTheBook() {
        borrowRecord = borrowRecordService.returnBook(borrowID);
    }

    @Then("Return date shouldn't be null")
    public void checkingReturnDate() {
        assertNotNull(borrowRecord.getReturnDate());
    }

    @Then("Update the Record status to returned")
    public void checkingBookStatus() {
        assertEquals("Returned", borrowRecord.getStatus());
    }

    @Then("Update book availability to available")
    public void checkingBookAvailability() {
        Book updatedBook = bookService.getBook(book.getId());
        assertTrue(updatedBook.isAvailable());
    }

}
