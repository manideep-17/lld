package com.lld.practice.questions;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Library Management System - LLD Interview Question
 * 
 * Requirements:
 * 1. Multiple libraries, each with multiple books
 * 2. Books can be borrowed and returned
 * 3. Users can search books by title, author, ISBN
 * 4. Track book availability and due dates
 * 5. Calculate fines for overdue books
 * 6. Support for different types of users (Student, Faculty, etc.)
 */
public class LibraryManagementSystem {
    
    public static void main(String[] args) {
        // Create library system
        LibrarySystem librarySystem = new LibrarySystem();
        
        // Create library
        Library centralLibrary = new Library("Central Library", "123 Main St");
        librarySystem.addLibrary(centralLibrary);
        
        // Add books
        Book book1 = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 
                             Arrays.asList("Programming", "Java"));
        Book book2 = new Book("978-0201616224", "Design Patterns", "Gang of Four", 
                             Arrays.asList("Programming", "Design"));
        Book book3 = new Book("978-0132350884", "Clean Code", "Robert Martin", 
                             Arrays.asList("Programming", "Best Practices"));
        
        centralLibrary.addBook(book1, 3);
        centralLibrary.addBook(book2, 2);
        centralLibrary.addBook(book3, 1);
        
        // Create users
        Student student = new Student("S001", "Alice Johnson", "alice@email.com");
        Faculty faculty = new Faculty("F001", "Dr. Bob Smith", "bob@university.edu");
        
        librarySystem.addUser(student);
        librarySystem.addUser(faculty);
        
        // Demonstrate functionality
        System.out.println("=== Library Management System Demo ===\n");
        
        // Search books
        System.out.println("Searching for 'Java' books:");
        List<Book> javaBooks = centralLibrary.searchBooks("Java");
        javaBooks.forEach(book -> System.out.println("- " + book.getTitle()));
        
        // Borrow books
        System.out.println("\nBorrowing books:");
        boolean borrowed1 = centralLibrary.borrowBook(book1.getIsbn(), student);
        boolean borrowed2 = centralLibrary.borrowBook(book2.getIsbn(), faculty);
        System.out.println("Student borrowed Effective Java: " + borrowed1);
        System.out.println("Faculty borrowed Design Patterns: " + borrowed2);
        
        // Check availability
        System.out.println("\nChecking availability:");
        System.out.println("Effective Java available: " + centralLibrary.isBookAvailable(book1.getIsbn()));
        System.out.println("Clean Code available: " + centralLibrary.isBookAvailable(book3.getIsbn()));
        
        // Return books
        System.out.println("\nReturning books:");
        boolean returned = centralLibrary.returnBook(book1.getIsbn(), student);
        System.out.println("Student returned Effective Java: " + returned);
        
        // Display user's borrowed books
        System.out.println("\nFaculty's borrowed books:");
        faculty.getBorrowedBooks().forEach(record -> 
            System.out.println("- " + record.getBook().getTitle() + " (Due: " + record.getDueDate() + ")"));
    }
}

// Book entity
class Book {
    private String isbn;
    private String title;
    private String author;
    private List<String> categories;
    
    public Book(String isbn, String title, String author, List<String> categories) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.categories = new ArrayList<>(categories);
    }
    
    // Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public List<String> getCategories() { return new ArrayList<>(categories); }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Objects.equals(isbn, book.isbn);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}

// User hierarchy
abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected List<BorrowRecord> borrowedBooks;
    protected int maxBooksAllowed;
    protected int borrowDurationDays;
    
    public User(String userId, String name, String email, int maxBooksAllowed, int borrowDurationDays) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.maxBooksAllowed = maxBooksAllowed;
        this.borrowDurationDays = borrowDurationDays;
        this.borrowedBooks = new ArrayList<>();
    }
    
    public boolean canBorrowMore() {
        return borrowedBooks.size() < maxBooksAllowed;
    }
    
    public abstract double calculateFine(BorrowRecord record);
    
    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<BorrowRecord> getBorrowedBooks() { return new ArrayList<>(borrowedBooks); }
    public int getBorrowDurationDays() { return borrowDurationDays; }
    
    public void addBorrowRecord(BorrowRecord record) {
        borrowedBooks.add(record);
    }
    
    public void removeBorrowRecord(BorrowRecord record) {
        borrowedBooks.remove(record);
    }
}

class Student extends User {
    public Student(String userId, String name, String email) {
        super(userId, name, email, 5, 14); // 5 books, 14 days
    }
    
    @Override
    public double calculateFine(BorrowRecord record) {
        long daysOverdue = record.getDaysOverdue();
        return daysOverdue > 0 ? daysOverdue * 0.50 : 0.0; // $0.50 per day
    }
}

class Faculty extends User {
    public Faculty(String userId, String name, String email) {
        super(userId, name, email, 10, 30); // 10 books, 30 days
    }
    
    @Override
    public double calculateFine(BorrowRecord record) {
        long daysOverdue = record.getDaysOverdue();
        return daysOverdue > 0 ? daysOverdue * 1.00 : 0.0; // $1.00 per day
    }
}

// Borrow record to track borrowed books
class BorrowRecord {
    private Book book;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    
    public BorrowRecord(Book book, User user, LocalDate borrowDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(user.getBorrowDurationDays());
    }
    
    public long getDaysOverdue() {
        LocalDate checkDate = returnDate != null ? returnDate : LocalDate.now();
        return checkDate.isAfter(dueDate) ? 
               java.time.temporal.ChronoUnit.DAYS.between(dueDate, checkDate) : 0;
    }
    
    public void markReturned() {
        this.returnDate = LocalDate.now();
    }
    
    // Getters
    public Book getBook() { return book; }
    public User getUser() { return user; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
}

// Library class
class Library {
    private String name;
    private String address;
    private Map<String, Book> books; // ISBN -> Book
    private Map<String, Integer> bookInventory; // ISBN -> Available count
    private Map<String, List<BorrowRecord>> activeLoans; // ISBN -> List of active loans
    
    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ConcurrentHashMap<>();
        this.bookInventory = new ConcurrentHashMap<>();
        this.activeLoans = new ConcurrentHashMap<>();
    }
    
    public void addBook(Book book, int quantity) {
        books.put(book.getIsbn(), book);
        bookInventory.put(book.getIsbn(), bookInventory.getOrDefault(book.getIsbn(), 0) + quantity);
        activeLoans.putIfAbsent(book.getIsbn(), new ArrayList<>());
    }
    
    public boolean isBookAvailable(String isbn) {
        return bookInventory.getOrDefault(isbn, 0) > 0;
    }
    
    public synchronized boolean borrowBook(String isbn, User user) {
        if (!books.containsKey(isbn)) {
            System.out.println("Book not found in library");
            return false;
        }
        
        if (!user.canBorrowMore()) {
            System.out.println("User has reached maximum book limit");
            return false;
        }
        
        if (!isBookAvailable(isbn)) {
            System.out.println("Book is not available");
            return false;
        }
        
        // Create borrow record
        Book book = books.get(isbn);
        BorrowRecord record = new BorrowRecord(book, user, LocalDate.now());
        
        // Update inventory and records
        bookInventory.put(isbn, bookInventory.get(isbn) - 1);
        activeLoans.get(isbn).add(record);
        user.addBorrowRecord(record);
        
        System.out.println("Book borrowed successfully: " + book.getTitle());
        return true;
    }
    
    public synchronized boolean returnBook(String isbn, User user) {
        List<BorrowRecord> loans = activeLoans.get(isbn);
        if (loans == null) return false;
        
        BorrowRecord recordToReturn = null;
        for (BorrowRecord record : loans) {
            if (record.getUser().getUserId().equals(user.getUserId()) && record.getReturnDate() == null) {
                recordToReturn = record;
                break;
            }
        }
        
        if (recordToReturn == null) {
            System.out.println("No active loan found for this book and user");
            return false;
        }
        
        // Mark as returned
        recordToReturn.markReturned();
        loans.remove(recordToReturn);
        user.removeBorrowRecord(recordToReturn);
        bookInventory.put(isbn, bookInventory.get(isbn) + 1);
        
        // Calculate fine if overdue
        double fine = user.calculateFine(recordToReturn);
        if (fine > 0) {
            System.out.println("Fine for overdue book: $" + fine);
        }
        
        System.out.println("Book returned successfully: " + recordToReturn.getBook().getTitle());
        return true;
    }
    
    public List<Book> searchBooks(String query) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                               book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                               book.getCategories().stream().anyMatch(cat -> 
                                   cat.toLowerCase().contains(query.toLowerCase())))
                .toList();
    }
    
    // Getters
    public String getName() { return name; }
    public String getAddress() { return address; }
}

// Library System - Main controller
class LibrarySystem {
    private Map<String, Library> libraries;
    private Map<String, User> users;
    
    public LibrarySystem() {
        this.libraries = new HashMap<>();
        this.users = new HashMap<>();
    }
    
    public void addLibrary(Library library) {
        libraries.put(library.getName(), library);
    }
    
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
    
    public Library getLibrary(String name) {
        return libraries.get(name);
    }
    
    public User getUser(String userId) {
        return users.get(userId);
    }
    
    public List<Book> searchBooksAcrossLibraries(String query) {
        Set<Book> allBooks = new HashSet<>();
        for (Library library : libraries.values()) {
            allBooks.addAll(library.searchBooks(query));
        }
        return new ArrayList<>(allBooks);
    }
} 