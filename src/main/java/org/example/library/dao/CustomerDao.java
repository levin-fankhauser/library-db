package org.example.library.dao;

import org.example.library.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/library";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "password";

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		String query = "SELECT * FROM customers";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				customers.add(new Customer(
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zip_code"),
						rs.getString("country")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;
	}

	public void addCustomer(Customer customer) {
		String query = "INSERT INTO customers (name, email, phone, address, city, state, zip_code, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, customer.name());
			pstmt.setString(2, customer.email());
			pstmt.setString(3, customer.phone());
			pstmt.setString(4, customer.address());
			pstmt.setString(5, customer.city());
			pstmt.setString(6, customer.state());
			pstmt.setString(7, customer.zipCode());
			pstmt.setString(8, customer.country());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomer(String email) {
		String query = "DELETE FROM customers WHERE email = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
