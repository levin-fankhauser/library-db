package org.example.library.dao;

import org.example.library.models.Book;

import java.util.ArrayList;
import java.util.List;

// Eine richtige Anbindung zur DB wird erst bei LB2 implementiert, da hier wie Besprochen noch keine DB benötigt wird, da der Dao in den Test gemockt wird. 
public class BookDao {
	private final List<Book> books = new ArrayList<>(List.of(
			new Book("Title1", "Author1", 2000, "ISBN1", "Genre1", 300, "English", "Description1"),
			new Book("Title2", "Author2", 2005, "ISBN2", "Genre2", 350, "German", "Description2")
	));

	public List<Book> getAllBooks() {
		return new ArrayList<>(books);
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public void deleteBook(String isbn) {
		books.removeIf(book -> book.isbn().equals(isbn));
	}
}