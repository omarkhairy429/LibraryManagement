package com.orange.omar.LibraryManagement;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.models.BorrowRecord;
import com.orange.omar.LibraryManagement.services.BookService;
import com.orange.omar.LibraryManagement.services.BorrowRecordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryManagementApplicationTests {
	@Autowired
	private BookService bookService;
	@Autowired
	private BorrowRecordService borrowRecordService;

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
	@Test
	void saveBookNonValidTest() {
		Book book = new Book(" ", "Deep Work", "Cal Newport", "Self Help");
		bookService.saveBook(book);

		Book checkBook = bookService.getBook(book.getId());
		assertNotEquals("Nagib Mahfouz" , checkBook.getAuthor());
	}

	@Test
	void userCanBorrowFiveBooksNormallyTest() {
		String userID = "USER-12345";
		List<Book> books = new ArrayList<>();


		for (int i = 0; i < 5; i++) {
			Book book = new Book();
			book.setId(UUID.randomUUID().toString());
			book.setTitle("Book " + (i + 1));
			book.setAuthor("Author " + (i + 1));
			book.setAvailable(true);
			bookService.saveBook(book);
			books.add(book);
		}


		for (int i = 0; i < 4; i++) {
			BorrowRecord record = new BorrowRecord();
			record.setUserID(userID);
			record.setBookID(books.get(i).getId());
			borrowRecordService.borrowBook(record);
		}

		/* Borrow the 5th book */
		BorrowRecord fifthRecord = new BorrowRecord();
		fifthRecord.setUserID(userID);
		fifthRecord.setBookID(books.get(4).getId());

		boolean exceptionExist = false;

		try {
			borrowRecordService.borrowBook(fifthRecord);
		} catch (Exception e) {
			exceptionExist = true;
		}

		assertFalse(exceptionExist);
	}



	@Test
	void userCannotBorrowMoreThanFiveBooksTest() {
		String userID = "USER-12346";
		List<Book> books = new ArrayList<>();


		for (int i = 0; i < 6; i++) {
			Book book = new Book();
			book.setId(UUID.randomUUID().toString());
			book.setTitle("Book " + (i + 1));
			book.setAuthor("Author " + (i + 1));
			book.setAvailable(true);
			bookService.saveBook(book);
			books.add(book);
		}


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

		boolean exceptionExist = false;

		try {
			borrowRecordService.borrowBook(sixthRecord);
		} catch (Exception e) {
			exceptionExist = true;
		}

		assertTrue(exceptionExist);
	}


	@Test
	void userWithThreeOverdueBooksIsBlockedTest() {
		String userID = "USER-123457";

		for (int i = 0; i < 3; i++) {
			Book book = new Book();
			book.setId(UUID.randomUUID().toString());
			book.setTitle("Book " + (i + 1));
			book.setAuthor("Author");
			book.setAvailable(true);
			bookService.saveBook(book);

			BorrowRecord record = new BorrowRecord();
			record.setUserID(userID);
			record.setBookID(book.getId());

			borrowRecordService.borrowBook(record);


			Date pastDate = new Date(System.currentTimeMillis() - 1000000L);
			record.setDueDate(pastDate);
		}


		Book newBook = new Book();
		newBook.setId(UUID.randomUUID().toString());
		newBook.setTitle("Omar's Biography");
		newBook.setAuthor("Omar Fayed");
		newBook.setAvailable(true);
		bookService.saveBook(newBook);

		BorrowRecord newRecord = new BorrowRecord();
		newRecord.setUserID(userID);
		newRecord.setBookID(newBook.getId());

		boolean exceptionExists = false;

		try {
			borrowRecordService.borrowBook(newRecord);
		} catch (Exception e) {
			exceptionExists = true;
		}

		assertTrue(exceptionExists);
	}

	@Test
	void userCanReturnBookTest() {

		Book book = new Book();
		book.setTitle("Book");
		book.setAuthor("Omar");
		book.setAvailable(true);
		bookService.saveBook(book);


		BorrowRecord record = new BorrowRecord();
		record.setUserID("USER123");
		record.setBookID(book.getId());

		BorrowRecord borrowedRecord = borrowRecordService.borrowBook(record);
		String borrowID = borrowedRecord.getBorrowID();


		BorrowRecord returnedRecord = borrowRecordService.returnBook(borrowID);


		assertNotNull(returnedRecord.getReturnDate());
		assertEquals("Returned", returnedRecord.getStatus());

		Book updatedBook = bookService.getBook(book.getId());
		assertTrue(updatedBook.isAvailable());
	}



}
