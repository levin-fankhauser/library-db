package org.example.library.services;

import org.example.library.dao.LendingDao;
import org.example.library.models.Lending;

import java.util.List;
import java.util.stream.Collectors;

public class LendingService {
	private final LendingDao lendingDao = new LendingDao();

	public List<Lending> getAllLendings() {
		return lendingDao.getAllLendings();
	}

	public void addLending(Lending lending) {
		lendingDao.addLending(lending);
	}

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
						lending.lost())));
	}

	public boolean isBookAvailable(String isbn) {
		return lendingDao.getAllLendings().stream()
				.noneMatch(lending -> lending.book().isbn().equals(isbn) && !lending.returned());
	}

	public List<Lending> listOverdueLendings(String currentDate) {
		return lendingDao.getAllLendings().stream()
				.filter(lending -> !lending.returned() && lending.returnDate().compareTo(currentDate) < 0)
				.collect(Collectors.toList());
	}
}