package org.example.library.models;

public record Lending(
		Book book,
		Customer customer,
		String lendingDate,
		String returnDate,
		boolean returned,
		boolean lost) {

}
