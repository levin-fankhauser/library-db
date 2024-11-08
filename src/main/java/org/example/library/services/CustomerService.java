package org.example.library.services;

import org.example.library.dao.CustomerDao;
import org.example.library.models.Customer;

import java.util.List;

public class CustomerService {
	private final CustomerDao customerDao = new CustomerDao();

	public List<Customer> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}

	public void deleteCustomer(String email) {
		customerDao.deleteCustomer(email);
	}

	public Customer findCustomerByEmail(String email) {
		return customerDao.getAllCustomers().stream()
				.filter(customer -> customer.email().equals(email))
				.findFirst()
				.orElse(null);
	}
}