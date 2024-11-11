package org.example.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.example.library.dao.BookDao;
import org.example.library.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;

class BookServiceTest {
	@Mock
	private BookDao bookDao;

	@InjectMocks
	private BookService bookService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllBooks() {
		List<Book> mockBooks = List.of(
				new Book("Title1", "Author1", 2000, "ISBN1", "Genre1", 300, "English", "Description1"),
				new Book("Title2", "Author2", 2005, "ISBN2", "Genre2", 350, "German", "Description2")
		);
		when(bookDao.getAllBooks()).thenReturn(mockBooks);

		List<Book> books = bookService.getAllBooks();

		assertNotNull(books);
		assertEquals(2, books.size());
		assertEquals("Title1", books.get(0).title());
		verify(bookDao, times(1)).getAllBooks();
	}

	@Test
	void testAddBook() {
		Book newBook = new Book("Title3", "Author3", 2010, "ISBN3", "Genre3", 250, "French", "Description3");

		bookService.addBook(newBook);

		verify(bookDao, times(1)).addBook(newBook);
	}

	@Test
	void testDeleteBook_BookExists() {
		String isbn = "ISBN1";
		doNothing().when(bookDao).deleteBook(isbn);

		bookService.deleteBook(isbn);

		verify(bookDao, times(1)).deleteBook(isbn);
	}

	@Test
	void testDeleteBook_BookDoesNotExist() {
		String isbn = "NonExistentISBN";
		doNothing().when(bookDao).deleteBook(isbn);

		bookService.deleteBook(isbn);

		verify(bookDao, times(1)).deleteBook(isbn);
	}

	@Test
	void testUpdateBook_BookExists() {
		String isbn = "ISBN1";
		Book updatedBook = new Book("UpdatedTitle", "UpdatedAuthor", 2020, isbn, "UpdatedGenre", 400, "Spanish", "UpdatedDescription");
		Book existingBook = new Book("OldTitle", "OldAuthor", 2000, isbn, "OldGenre", 300, "English", "OldDescription");

		when(bookDao.getAllBooks()).thenReturn(List.of(existingBook));
		doNothing().when(bookDao).deleteBook(isbn);
		doNothing().when(bookDao).addBook(updatedBook);

		bookService.updateBook(isbn, updatedBook);

		verify(bookDao, times(1)).deleteBook(isbn);
		verify(bookDao, times(1)).addBook(updatedBook);
	}

	@Test
	void testUpdateBook_BookDoesNotExist() {
		String isbn = "NonExistentISBN";
		Book updatedBook = new Book("UpdatedTitle", "UpdatedAuthor", 2020, isbn, "UpdatedGenre", 400, "Spanish", "UpdatedDescription");

		when(bookDao.getAllBooks()).thenReturn(List.of());

		bookService.updateBook(isbn, updatedBook);

		verify(bookDao, never()).deleteBook(anyString());
		verify(bookDao, never()).addBook(any(Book.class));
	}

	@Test
	void testFindBookByIsbn_BookExists() {
		String isbn = "ISBN1";
		Book existingBook = new Book("Title1", "Author1", 2000, isbn, "Genre1", 300, "English", "Description1");

		when(bookDao.getAllBooks()).thenReturn(List.of(existingBook));

		Book foundBook = bookService.findBookByIsbn(isbn);

		assertNotNull(foundBook);
		assertEquals(isbn, foundBook.isbn());
	}

	@Test
	void testFindBookByIsbn_BookDoesNotExist() {
		String isbn = "NonExistentISBN";

		when(bookDao.getAllBooks()).thenReturn(List.of());

		Book foundBook = bookService.findBookByIsbn(isbn);

		assertNull(foundBook);
	}
}
