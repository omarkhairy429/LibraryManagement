package com.orange.omar.LibraryManagement.steps;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.services.BookService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteBookSteps {
    private Book book;
    @Autowired
    private BookService bookService;
    private String bookID;

    @Given("There is a book stored in database with wrong author name")
    public void havingBookWithWrongName() {
        book = new Book();
        book.setTitle("Book");
        book.setAuthor("Omarrrrr");
        book.setAvailable(true);
        Book savedBook = bookService.saveBook(book);

        bookID = savedBook.getId();
    }

    @When("I try to delete that book")
    public void TryingToDeleteTheBook() {
        bookService.deleteBook(bookID);
    }

    @Then("It must be removed from database")
    public void checkRemovedBook() {
        Book newBook = new Book();
        boolean exceptionExists = false;
        try {
            newBook = bookService.getBook(bookID);
        }
        catch (Exception e) {
            exceptionExists = true;
        }

        assertTrue(exceptionExists);

    }

}
