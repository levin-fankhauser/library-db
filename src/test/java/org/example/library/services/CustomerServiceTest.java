package org.example.library.services;

import org.example.library.dao.CustomerDao;
import org.example.library.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

	@Mock
	private CustomerDao customerDao;

	@InjectMocks
	private CustomerService customerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllCustomersReturnsCustomers() {
		List<Customer> mockCustomers = List.of(new Customer("Alice", "alice@example.com", "5551234",
				"100 Some Street", "Town", "Region", "12345", "Country"));
		when(customerDao.getAllCustomers()).thenReturn(mockCustomers);

		List<Customer> customers = customerService.getAllCustomers();
		assertEquals(1, customers.size());
		assertEquals("Alice", customers.get(0).name());
		verify(customerDao, times(1)).getAllCustomers();
	}

	@Test
	void testAddCustomerAddsNewCustomer() {
		Customer newCustomer = new Customer("Bob", "bob@example.com", "5556789", "200 Any Street", "City",
				"State", "54321", "Country");

		customerService.addCustomer(newCustomer);
		verify(customerDao, times(1)).addCustomer(newCustomer);
	}

	@Test
	void testDeleteCustomer() {
		String existingEmail = "john@example.com";
		String nonExistentEmail = "nonexistent@example.com";

		when(customerDao.getAllCustomers()).thenReturn(
				List.of(new Customer("John Doe", existingEmail, "1234567890", "123 Street", "City", "State", "12345", "Country"))
		);
		doNothing().when(customerDao).deleteCustomer(existingEmail);

		customerService.deleteCustomer(existingEmail);
		verify(customerDao, times(1)).deleteCustomer(existingEmail);

		when(customerDao.getAllCustomers()).thenReturn(List.of());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(nonExistentEmail));

		assertEquals("Customer with email " + nonExistentEmail + " does not exist.", exception.getMessage());
	}

	@Test
	void testFindCustomerByEmail_CustomerExists() {
		Customer customer = new Customer("Jane", "jane@example.com", "123456789",
				"300 Main St", "Village", "Province", "67890", "Country");
		when(customerDao.getAllCustomers()).thenReturn(List.of(customer));

		Customer foundCustomer = customerService.findCustomerByEmail("jane@example.com");
		assertNotNull(foundCustomer);
		assertEquals("Jane", foundCustomer.name());
		assertEquals("jane@example.com", foundCustomer.email());
	}

	@Test
	void testFindCustomerByEmail_CustomerDoesNotExist() {
		when(customerDao.getAllCustomers()).thenReturn(List.of());

		Customer foundCustomer = customerService.findCustomerByEmail("nonexistent@example.com");
		assertNull(foundCustomer);
	}

}
