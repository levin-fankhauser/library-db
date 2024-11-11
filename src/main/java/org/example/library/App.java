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

		// Add a new book
		Book newBook = new Book("Title3", "Author3", 2010, "ISBN3", "Genre3", 400, "French", "Description3");
		bookService.addBook(newBook);

		// Add a new customer
		Customer newCustomer = new Customer("Alice Johnson", "alice@example.com", "1122334455", "789 Boulevard", "City", "State", "54321", "Country");
		customerService.addCustomer(newCustomer);

		// Add a new lending
		Lending newLending = new Lending(newBook, newCustomer, "2023-03-01", "2023-03-15", false, false);
		lendingService.addLending(newLending);

		// Return a lending
		lendingService.returnLending("ISBN3", "alice@example.com");

		// Delete a book
		bookService.deleteBook("ISBN1");

		// Delete a customer
		customerService.deleteCustomer("john@example.com");

		// Update a book
		Book updatedBook = new Book("Updated Title", "Updated Author", 2020, "ISBN2", "Updated Genre", 500, "Spanish", "Updated Description");
		bookService.updateBook("ISBN2", updatedBook);

		// Find a book by ISBN
		Book foundBook = bookService.findBookByIsbn("ISBN2");
		System.out.println("\nFound Book: " + foundBook);

		// Find a customer by email
		Customer foundCustomer = customerService.findCustomerByEmail("alice@example.com");
		System.out.println("\nFound Customer: " + foundCustomer);

		// Check if a book is available
		boolean isAvailable = lendingService.isBookAvailable("ISBN3");
		System.out.println("\nIs Book ISBN3 Available? " + isAvailable);

		// List overdue lendings
		List<Lending> overdueLendings = lendingService.listOverdueLendings("2023-04-01");
		System.out.println("\nOverdue Lendings:");
		overdueLendings.forEach(System.out::println);

		// Display updated lists
		System.out.println("\nUpdated Books:");
		books = bookService.getAllBooks();
		books.forEach(System.out::println);

		System.out.println("\nUpdated Customers:");
		customers = customerService.getAllCustomers();
		customers.forEach(System.out::println);

		System.out.println("\nUpdated Lendings:");
		lendings = lendingService.getAllLendings();
		lendings.forEach(System.out::println);
	}
}