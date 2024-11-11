package org.example.library.dao;

import org.example.library.models.Customer;

import java.util.ArrayList;
import java.util.List;

// Eine richtige Anbindung zur DB wird erst bei LB2 implementiert, da hier wie Besprochen noch keine DB benötigt wird, da der Dao in den Test gemockt wird. 
public class CustomerDao {
	private final List<Customer> customers = new ArrayList<>(List.of(
			new Customer("John Doe", "john@example.com", "1234567890", "123 Street", "City", "State", "12345", "Country"),
			new Customer("Jane Smith", "jane@example.com", "0987654321", "456 Avenue", "City", "State", "67890", "Country")
	));

	public List<Customer> getAllCustomers() {
		return new ArrayList<>(customers);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void deleteCustomer(String email) {
		customers.removeIf(customer -> customer.email().equals(email));
	}
}