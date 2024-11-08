package org.example.library.dao;

import org.example.library.models.Lending;

import java.util.ArrayList;
import java.util.List;

public class LendingDao {
	private final List<Lending> lendings = new ArrayList<>();

	public List<Lending> getAllLendings() {
		return new ArrayList<>(lendings);
	}

	public void addLending(Lending lending) {
		lendings.add(lending);
	}

	public void returnLending(String isbn, String email) {
		for (Lending lending : lendings) {
			if (lending.book().isbn().equals(isbn) && lending.customer().email().equals(email) && !lending.returned()) {
				lendings.set(lendings.indexOf(lending), new Lending(
						lending.book(), lending.customer(), lending.lendingDate(), lending.returnDate(), true, lending.lost()
				));
				break;
			}
		}
	}
}