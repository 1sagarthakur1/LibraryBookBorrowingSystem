package com.library;

import com.library.dao.BorrowTask;
import com.library.dao.LibraryDao;
import com.library.dao.LibraryDaoImpl;
import com.library.model.Book;
import com.library.model.User;
import com.library.model.BorrowedBook;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws SQLException {

            LibraryDao libraryDao = new LibraryDaoImpl();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n===== Library Menu =====");
                System.out.println("1. Add Book");
                System.out.println("2. Add User");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. List Books Borrowed by User");
                System.out.println("6. List Users With Unreturned Books");
                System.out.println("7. Show Borrow History");
                System.out.println("8. Simulate Multithreaded Borrowing");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter book author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter number of copies: ");
                        int copies = scanner.nextInt();
                        libraryDao.addBook(new Book(0, title, author, copies));
                        System.out.println("Book added.");
                        break;
                    case 2:
                        System.out.print("Enter user name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        libraryDao.addUser(new User(0, name, email));
                        System.out.println("User added.");
                        break;
                    case 3:
                        System.out.print("Enter user ID: ");
                        int userId = scanner.nextInt();
                        System.out.print("Enter book ID: ");
                        int bookId = scanner.nextInt();
                        if (libraryDao.borrowBook(userId, bookId))
                            System.out.println("Book borrowed.");
                        else
                            System.out.println("Book not available.");
                        break;
                    case 4:
                        System.out.print("Enter user ID: ");
                        int returnUserId = scanner.nextInt();
                        System.out.print("Enter book ID: ");
                        int returnBookId = scanner.nextInt();
                        libraryDao.returnBook(returnUserId, returnBookId);
                        System.out.println("Book returned.");
                        break;
                    case 5:
                        System.out.print("Enter user ID: ");
                        int uid = scanner.nextInt();
                        List<String> books = libraryDao.listBooksBorrowedByUser(uid);
                        books.forEach(System.out::println);
                        break;
                    case 6:
                        List<User> users = libraryDao.listUsersWithUnreturnedBooks();
                        users.forEach(u -> System.out.println(u.getName() + " - " + u.getEmail()));
                        break;
                    case 7:
                        List<BorrowedBook> history = libraryDao.getBorrowHistory();
                        history.forEach(System.out::println);
                        break;
                    case 8:
                        System.out.print("Enter book ID to simulate borrowing: ");
                        int simulateBookId = scanner.nextInt();

                        Thread t1 = new Thread(new BorrowTask(libraryDao, 1, simulateBookId));
                        Thread t2 = new Thread(new BorrowTask(libraryDao, 2, simulateBookId));
                        Thread t3 = new Thread(new BorrowTask(libraryDao, 3, simulateBookId));

                        t1.start();
                        t2.start();
                        t3.start();

                        try {
                            t1.join();
                            t2.join();
                            t3.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option.");
                }
            }
    }
}
