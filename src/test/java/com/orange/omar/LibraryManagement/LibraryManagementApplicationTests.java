package com.orange.omar.LibraryManagement;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.services.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryManagementApplicationTests {
	@Autowired
	private BookService bookService;

	@Test
	void contextLoads() {
	}

	// Happy Scenario (should pass)
	@Test
	void saveBookTValidest() {
		Book book = new Book(" ", "Deep Work", "Cal Newport", "Self Help");
		bookService.saveBook(book);

		Book checkBook = bookService.getBook(book.getId());
		assertEquals("Cal Newport", checkBook.getAuthor());
	}

	// Negative Scenario (should fail)
//	@Test
//	void saveBookNonValidTest() {
//		Book book = new Book(" ", "Deep Work", "Cal Newport", "Self Help");
//		bookService.saveBook(book);
//
//		Book checkBook = bookService.getBook(book.getId());
//		assertEquals("Nagib Mahfouz" , checkBook.getAuthor());
//	}


}
