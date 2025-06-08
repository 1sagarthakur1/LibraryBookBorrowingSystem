# 📚 Library Book Borrowing System

A **console-based Java application** to simulate a library system where multiple users can borrow, return, and view books concurrently. This project integrates **multithreading**, **JDBC**, **MySQL**, and is built using **Maven**.

---

## 🚀 Project Goals

- Understand the integration of:
  - JDBC-based database operations
  - Multithreading for concurrent user simulations
  - Maven for project build and dependency management

---

## 🧩 Features

### ✅ Core Features

- **Book & User Management (JDBC + DB)**
  - Add new books and users
  - Borrow a book
  - Return a book
  - Track available copies

- **Joins**
  - List all books borrowed by a specific user
  - List users who haven’t returned books
  - Get full book borrow history with user info

- **Multithreading**
  - Simulate concurrent users borrowing books
  - Use synchronization to avoid race conditions

- **Maven Integration**
  - Proper Maven project structure
  - Dependencies managed (JDBC Driver)

---

## 🛠️ Tech Stack

- Java 8+
- JDBC
- MySQL
- Maven

---

## 🧱 Database Schema

### `books`
| Column         | Type     |
|----------------|----------|
| book_id        | INT PK   |
| title          | VARCHAR  |
| author         | VARCHAR  |
| available_copies | INT    |

### `users`
| Column   | Type     |
|----------|----------|
| user_id  | INT PK   |
| name     | VARCHAR  |
| email    | VARCHAR  |

### `borrowed_books`
| Column       | Type     |
|--------------|----------|
| borrow_id    | INT PK   |
| user_id      | INT FK   |
| book_id      | INT FK   |
| borrow_date  | DATE     |
| return_date  | DATE NULLABLE |
