package org.example.library.services;

import org.example.library.dao.LendingDao;
import org.example.library.models.Lending;

import java.util.List;

public class LendingService {

	private final LendingDao lendingDao;

	public LendingService(LendingDao lendingDao) {
		this.lendingDao = lendingDao;
	}

	public List<Lending> getAllLendings() {
		return lendingDao.getAllLendings();
	}

	public void addLending(Lending lending) {
		lendingDao.addLending(lending);
	}

	public boolean isBookAvailable(String isbn) {
		return lendingDao.getAllLendings().stream().noneMatch(lending -> lending.book().isbn().equals(isbn) && !lending.returned());
	}

	// TDD
	public void returnLending(String isbn, String email) {
		lendingDao.getAllLendings()
				.stream()
				.filter(l -> l.book().isbn().equals(isbn) && l.customer().email().equals(email) && !l.returned())
				.findFirst()
				.ifPresent(lending -> lendingDao.updateLending(new Lending(lending.book(),
						lending.customer(),
						lending.lendingDate(),
						lending.returnDate(),
						true,
						false)));
	}
}