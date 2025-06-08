package com.library.dao;
public class BorrowTask implements Runnable {
    private final LibraryDao libraryDao;
    private final int userId;
    private final int bookId;

    public BorrowTask(LibraryDao libraryDao, int userId, int bookId) {
        this.libraryDao = libraryDao;
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public void run() {
        try {
            if (libraryDao.borrowBook(userId, bookId)) {
                System.out.println("User " + userId + " successfully borrowed book " + bookId);
            } else {
                System.out.println("User " + userId + " failed to borrow book " + bookId + " (not available)");
            }
        } catch (Exception e) {
            System.out.println("user not exist");
        }
    }
}

