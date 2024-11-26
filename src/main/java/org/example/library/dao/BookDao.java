package org.example.library.dao;

import org.example.library.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/library";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "password";

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		String query = "SELECT * FROM books";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				books.add(new Book(
						rs.getString("title"),
						rs.getString("author"),
						rs.getInt("year"),
						rs.getString("isbn"),
						rs.getString("genre"),
						rs.getInt("pages"),
						rs.getString("language"),
						rs.getString("description")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	public void addBook(Book book) {
		String query = "INSERT INTO books (title, author, year, isbn, genre, pages, language, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, book.title());
			pstmt.setString(2, book.author());
			pstmt.setInt(3, book.year());
			pstmt.setString(4, book.isbn());
			pstmt.setString(5, book.genre());
			pstmt.setInt(6, book.pages());
			pstmt.setString(7, book.language());
			pstmt.setString(8, book.description());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBook(String isbn) {
		String query = "DELETE FROM books WHERE isbn = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, isbn);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
