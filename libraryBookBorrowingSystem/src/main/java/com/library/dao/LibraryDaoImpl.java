package com.library.dao;

import com.library.model.Book;
import com.library.model.User;
import com.library.model.BorrowedBook;
import com.library.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryDaoImpl implements  LibraryDao{
    private final Connection connection;

    public LibraryDaoImpl() {
        this.connection = DBConnection.takeConnection();
    }

    @Override
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, available_copies) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getAvailableCopies());
            stmt.executeUpdate();
        }
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
        }
    }

    @Override
    public synchronized boolean borrowBook(int userId, int bookId) throws SQLException {
        String checkSql = "SELECT available_copies FROM books WHERE book_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt("available_copies") > 0) {
                String borrowSql = "INSERT INTO borrowed_books (user_id, book_id, borrow_date) VALUES (?, ?, ?)";
                try (PreparedStatement borrowStmt = connection.prepareStatement(borrowSql)) {
                    borrowStmt.setInt(1, userId);
                    borrowStmt.setInt(2, bookId);
                    borrowStmt.setDate(3, Date.valueOf(LocalDate.now()));
                    borrowStmt.executeUpdate();
                }
                String updateSql = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public void returnBook(int userId, int bookId) throws SQLException {
        String updateBorrowSql = "UPDATE borrowed_books SET return_date = ? WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateBorrowSql)) {
            updateStmt.setDate(1, Date.valueOf(LocalDate.now()));
            updateStmt.setInt(2, userId);
            updateStmt.setInt(3, bookId);
            updateStmt.executeUpdate();
        }
        String updateBookSql = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateBookSql)) {
            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();
        }
    }

    @Override
    public List<String> listBooksBorrowedByUser(int userId) throws SQLException {
        String sql = "SELECT b.title, b.author FROM books b JOIN borrowed_books bb ON b.book_id = bb.book_id WHERE bb.user_id = ?";
        List<String> books = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(rs.getString("title") + " by " + rs.getString("author"));
            }
        }
        return books;
    }

    @Override
    public List<User> listUsersWithUnreturnedBooks() throws SQLException {
        String sql = "SELECT DISTINCT u.user_id, u.name, u.email FROM users u WHERE u.user_id IN (SELECT user_id FROM borrowed_books WHERE return_date IS NULL)";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email")));
            }
        }
        return users;
    }

    @Override
    public List<BorrowedBook> getBorrowHistory() throws SQLException {
        String sql = "SELECT * FROM borrowed_books";
        List<BorrowedBook> history = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BorrowedBook bb = new BorrowedBook(
                        rs.getInt("borrow_id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("borrow_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null
                );
                history.add(bb);
            }
        }
        return history;
    }
}
