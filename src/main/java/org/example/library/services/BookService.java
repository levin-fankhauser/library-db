package org.example.library.services;

import org.example.library.dao.BookDao;
import org.example.library.models.Book;

import java.util.List;

public class BookService {
	private final BookDao bookDao = new BookDao();

	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}

	public void addBook(Book book) {
		bookDao.addBook(book);
	}

	public void deleteBook(String isbn) {
		bookDao.deleteBook(isbn);
	}

	public void updateBook(String isbn, Book updatedBook) {
		Book book = findBookByIsbn(isbn);
		if (book != null) {
			bookDao.deleteBook(isbn);
			bookDao.addBook(updatedBook);
		}
	}

	public Book findBookByIsbn(String isbn) {
		return bookDao.getAllBooks().stream()
				.filter(book -> book.isbn().equals(isbn))
				.findFirst()
				.orElse(null);
	}
}