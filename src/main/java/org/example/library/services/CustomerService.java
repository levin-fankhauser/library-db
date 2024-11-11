package org.example.library.services;

import org.example.library.dao.CustomerDao;
import org.example.library.models.Customer;

import java.util.List;

public class CustomerService {
	private final CustomerDao customerDao;

	public CustomerService(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public List<Customer> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}

	public void deleteCustomer(String email) {
		if (customerDao.getAllCustomers().stream().noneMatch(c -> c.email().equals(email))) {
			throw new IllegalArgumentException("Customer with email " + email + " does not exist.");
		}
		customerDao.deleteCustomer(email);
	}

	public Customer findCustomerByEmail(String email) {
		return customerDao.getAllCustomers().stream()
				.filter(customer -> customer.email().equals(email))
				.findFirst()
				.orElse(null);
	}
}