package org.example.library.dao;

import org.example.library.models.Lending;

import java.util.ArrayList;
import java.util.List;

// Eine richtige Anbindung zur DB wird erst bei LB2 implementiert, da hier wie Besprochen noch keine DB benötigt wird, da der Dao in den Test gemockt wird. 
public class LendingDao {
	private final List<Lending> lendings = new ArrayList<>();

	public List<Lending> getAllLendings() {
		return new ArrayList<>(lendings);
	}

	public void addLending(Lending lending) {
		lendings.add(lending);
	}

	public void updateLending(Lending lending) {
		lendings.stream()
				.filter(l -> l.book().equals(lending.book()) &&
						l.customer().equals(lending.customer()) &&
						l.lendingDate().equals(lending.lendingDate()))
				.findFirst()
				.ifPresent(l -> lendings.set(lendings.indexOf(l), lending));
	}
}