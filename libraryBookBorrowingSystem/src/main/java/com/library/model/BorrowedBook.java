package com.library.model;

import java.time.LocalDate;

public class BorrowedBook {
    private int id;
    private int userId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowedBook() {}

    public BorrowedBook(int id, int userId, int bookId, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowedBook [userId=" + userId + ", bookId=" + bookId +
                ", borrowDate=" + borrowDate + ", returnDate=" + returnDate + "]";
    }
}
