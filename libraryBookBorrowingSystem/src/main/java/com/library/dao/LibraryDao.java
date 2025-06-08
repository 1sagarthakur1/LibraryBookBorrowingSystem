package com.library.dao;

import com.library.model.Book;
import com.library.model.User;
import com.library.model.BorrowedBook;
import java.sql.SQLException;
import java.util.List;

public interface LibraryDao {

    void addBook(Book book) throws SQLException;

    void addUser(User user) throws SQLException;

    boolean borrowBook(int userId, int bookId) throws SQLException;

    void returnBook(int userId, int bookId) throws SQLException;

    List<String> listBooksBorrowedByUser(int userId) throws SQLException;

    List<User> listUsersWithUnreturnedBooks() throws SQLException;

    List<BorrowedBook> getBorrowHistory() throws SQLException;
}
