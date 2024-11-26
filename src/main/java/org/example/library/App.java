package org.example.library;

import org.example.library.dao.BookDao;
import org.example.library.dao.CustomerDao;
import org.example.library.dao.LendingDao;
import org.example.library.models.Book;
import org.example.library.models.Customer;
import org.example.library.models.Lending;
import org.example.library.services.BookService;
import org.example.library.services.CustomerService;
import org.example.library.services.LendingService;

import java.util.List;

public class App {
	private static final BookService bookService = new BookService(new BookDao());
	private static final CustomerService customerService = new CustomerService(new CustomerDao());
	private static final LendingService lendingService = new LendingService(new LendingDao());

	public static void main(String[] args) {
		// Display all books
		System.out.println("Books:");
		List<Book> books = bookService.getAllBooks();
		books.forEach(System.out::println);

		// Display all customers
		System.out.println("\nCustomers:");
		List<Customer> customers = customerService.getAllCustomers();
		customers.forEach(System.out::println);

		// Display all lendings
		System.out.println("\nLendings:");
		List<Lending> lendings = lendingService.getAllLendings();
		lendings.forEach(System.out::println);
	}
}