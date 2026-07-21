package com.orange.omar.LibraryManagement.steps;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.services.BookService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class GetBookSteps {
    @Autowired
    BookService bookService;
    private String bookId;
    private Book book;
    private String author;
    private String title;

    @Given("A book exists in the database")
    public void saveBookInDatabase() {
        book = new Book();
        book.setAuthor("Omar Fayed");
        book.setCategory("Computer Science");
        book.setTitle("How to write Clean code 'not like my code -_-'");
        bookService.saveBook(book);
        bookId = book.getId();
    }

    @When("I retreive the book by id")
    public void getBookByID() {
        Book retreivedBook = bookService.getBook(bookId);
        author = retreivedBook.getAuthor();
        title = retreivedBook.getTitle();
    }

    @Then("I should see the book details")
    public void IShouldSeeBookData() {
        assertEquals("Omar Fayed", author);
        assertEquals("How to write Clean code 'not like my code -_-'", title);
    }
}
