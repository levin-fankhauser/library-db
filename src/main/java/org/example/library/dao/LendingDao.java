package org.example.library.dao;

import org.example.library.models.Book;
import org.example.library.models.Customer;
import org.example.library.models.Lending;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LendingDao {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/library";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "password";

	public List<Lending> getAllLendings() {
		List<Lending> lendings = new ArrayList<>();
		String query = """
            SELECT b.title, b.author, b.year, b.isbn, b.genre, b.pages, b.language, b.description,
                   c.name, c.email, c.phone, c.address, c.city, c.state, c.zip_code, c.country,
                   l.lending_date, l.return_date, l.returned, l.lost
            FROM lendings l
            JOIN books b ON l.book_id = b.id
            JOIN customers c ON l.customer_id = c.id
        """;

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Book book = new Book(
						rs.getString("title"),
						rs.getString("author"),
						rs.getInt("year"),
						rs.getString("isbn"),
						rs.getString("genre"),
						rs.getInt("pages"),
						rs.getString("language"),
						rs.getString("description")
				);

				Customer customer = new Customer(
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zip_code"),
						rs.getString("country")
				);

				Lending lending = new Lending(
						book,
						customer,
						rs.getString("lending_date"),
						rs.getString("return_date"),
						rs.getBoolean("returned"),
						rs.getBoolean("lost")
				);

				lendings.add(lending);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lendings;
	}

	public void addLending(Lending lending) {
		String query = """
            INSERT INTO lendings (book_id, customer_id, lending_date, return_date, returned, lost)
            VALUES (
                (SELECT id FROM books WHERE isbn = ?),
                (SELECT id FROM customers WHERE email = ?),
                ?, ?, ?, ?
            )
        """;

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, lending.book().isbn());
			pstmt.setString(2, lending.customer().email());
			pstmt.setString(3, lending.lendingDate());
			pstmt.setString(4, lending.returnDate());
			pstmt.setBoolean(5, lending.returned());
			pstmt.setBoolean(6, lending.lost());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateLending(Lending lending) {
		String query = """
            UPDATE lendings 
            SET return_date = ?, 
                returned = ?, 
                lost = ? 
            WHERE book_id = (SELECT id FROM books WHERE isbn = ?) 
              AND customer_id = (SELECT id FROM customers WHERE email = ?) 
              AND lending_date = ?
        """;

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, lending.returnDate());
			pstmt.setBoolean(2, lending.returned());
			pstmt.setBoolean(3, lending.lost());
			pstmt.setString(4, lending.book().isbn());
			pstmt.setString(5, lending.customer().email());
			pstmt.setString(6, lending.lendingDate());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteLending(String isbn, String email, String lendingDate) {
		String query = """
            DELETE FROM lendings 
            WHERE book_id = (SELECT id FROM books WHERE isbn = ?) 
              AND customer_id = (SELECT id FROM customers WHERE email = ?) 
              AND lending_date = ?
        """;

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, isbn);
			pstmt.setString(2, email);
			pstmt.setString(3, lendingDate);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
