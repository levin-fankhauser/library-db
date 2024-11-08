package org.example.library.models;

public record Book(
		String title,
		String author,
		int year,
		String isbn,
		String genre,
		int pages,
		String language,
		String description) {

}
