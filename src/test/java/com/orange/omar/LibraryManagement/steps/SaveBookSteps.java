package com.orange.omar.LibraryManagement.steps;


import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.services.BookService;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;


@CucumberContextConfiguration
@SpringBootTest
public class SaveBookSteps {
    @Autowired BookService bookService;
    private Book book;
    private String author;
    private String title;

    @Given("I have new book titled {string} and author name {string}")
    public void havingTheNewBook(String title, String author) {
        book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
    }

    @When("I save the book in the database")
    public void saveTheBook() {
        bookService.saveBook(book);
    }

    @Then("I can get the book from the database")
    public void gettingBookFromDatabase() {
        Book retreivedBook = bookService.getBook(book.getId());
        author = retreivedBook.getAuthor();
        title =  retreivedBook.getTitle();
        assertNotNull(retreivedBook);
    }

    @Then("I should get the correct book details")
    public void seingTheCorrectBookDetails() {
        assertEquals("Omar", author);
        assertEquals("Clean Code", title);
    }
}
