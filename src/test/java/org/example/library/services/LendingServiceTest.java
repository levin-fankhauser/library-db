package org.example.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.example.library.dao.LendingDao;
import org.example.library.models.Book;
import org.example.library.models.Customer;
import org.example.library.models.Lending;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class LendingServiceTest {

	@Mock
	private LendingDao lendingDaoMock;

	@InjectMocks
	private LendingService lendingService;

	private final Book book1 = new Book("Book Title 1", "Author 1", 2020, "12345", "Genre", 200, "English", "Description 1");
	private final Book book2 = new Book("Book Title 2", "Author 2", 2021, "67890", "Genre", 300, "English", "Description 2");

	private final Customer customer1 = new Customer("John Doe", "john@example.com", "1234567890", "Address 1", "City", "State", "12345", "Country");
	private final Customer customer2 = new Customer("Jane Doe", "jane@example.com", "0987654321", "Address 2", "City", "State", "67890", "Country");

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllLendings() {
		Lending lending1 = new Lending(book1, customer1, "2024-01-01", "2024-01-10", false, false);
		Lending lending2 = new Lending(book2, customer2, "2024-02-01", "2024-02-10", false, false);
		when(lendingDaoMock.getAllLendings()).thenReturn(Arrays.asList(lending1, lending2));

		List<Lending> lendings = lendingService.getAllLendings();

		assertEquals(2, lendings.size());
		assertTrue(lendings.contains(lending1));
		assertTrue(lendings.contains(lending2));
		verify(lendingDaoMock, times(1)).getAllLendings();
	}

	@Test
	void testAddLending() {
		Lending lending = new Lending(book1, customer1, "2024-01-01", "2024-01-10", false, false);

		lendingService.addLending(lending);

		verify(lendingDaoMock, times(1)).addLending(lending);
	}

	@Test
	void testIsBookAvailable_LendingExistsOne() {
		Lending lending = new Lending(book1, customer1, "2024-10-01", "2024-10-15", true, false);
		LendingDao lendingDaoMock = mock(LendingDao.class);
		when(lendingDaoMock.getAllLendings()).thenReturn(List.of(lending));

		LendingService lendingService = new LendingService(lendingDaoMock);

		assertTrue(lendingService.isBookAvailable(book1.isbn()));
	}

	@Test
	void testIsBookAvailable_LendingExistsTwo() {
		Lending lending = new Lending(book2, customer1, "2024-10-01", "2024-10-15", false, false);
		LendingDao lendingDaoMock = mock(LendingDao.class);
		when(lendingDaoMock.getAllLendings()).thenReturn(List.of(lending));

		LendingService lendingService = new LendingService(lendingDaoMock);

		assertTrue(lendingService.isBookAvailable(book1.isbn()));
	}

	@Test
	void testIsBookAvailable_LendingDoesNotExist() {
		Lending lending = new Lending(book1, customer1, "2024-10-01", "2024-10-15", false, false);
		LendingDao lendingDaoMock = mock(LendingDao.class);
		when(lendingDaoMock.getAllLendings()).thenReturn(List.of(lending));

		LendingService lendingService = new LendingService(lendingDaoMock);

		assertFalse(lendingService.isBookAvailable(book1.isbn()));
	}

	// TDD
	// Testfall, falls die Ausleihe erfolgreich zurückgegeben wird
	@Test
	void testReturnLendingSuccessfully() {
		Lending lending = new Lending(book1, customer1, "2024-01-01", "2024-01-10", false, false);
		when(lendingDaoMock.getAllLendings()).thenReturn(List.of(lending));

		lendingService.returnLending("12345", "john@example.com");

		verify(lendingDaoMock, times(1)).updateLending(argThat(l -> l.returned() && l.book().isbn().equals("12345") && l.customer()
				.email()
				.equals("john@example.com")));
	}

	// Testfall, falls keine passende Ausleihe gefunden wird
	@Test
	void testReturnLendingNoMatchingLending() {
		Lending lending = new Lending(book1, customer1, "2024-01-01", "2024-01-10", false, false);
		when(lendingDaoMock.getAllLendings()).thenReturn(List.of(lending));

		lendingService.returnLending("67890", "john@example.com");

		verify(lendingDaoMock, never()).updateLending(any());
	}

	// Testfall, falls die Ausleihe bereits zurückgegeben wurde
	@Test
	void testReturnLendingAlreadyReturned() {
		Lending lending = new Lending(book1, customer1, "2024-01-01", "2024-01-10", true, false);
		when(lendingDaoMock.getAllLendings()).thenReturn(List.of(lending));

		lendingService.returnLending("12345", "john@example.com");

		verify(lendingDaoMock, never()).updateLending(any());
	}

}

