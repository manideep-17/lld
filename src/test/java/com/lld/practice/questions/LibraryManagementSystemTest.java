package com.lld.practice.questions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Library Management System
 */
class LibraryManagementSystemTest {
    
    private Library library;
    private Book book1, book2, book3;
    private Student student;
    private Faculty faculty;
    
    @BeforeEach
    void setUp() {
        library = new Library("Test Library", "123 Test St");
        
        book1 = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 
                        Arrays.asList("Programming", "Java"));
        book2 = new Book("978-0201616224", "Design Patterns", "Gang of Four", 
                        Arrays.asList("Programming", "Design"));
        book3 = new Book("978-0132350884", "Clean Code", "Robert Martin", 
                        Arrays.asList("Programming", "Best Practices"));
        
        student = new Student("S001", "Alice Johnson", "alice@email.com");
        faculty = new Faculty("F001", "Dr. Bob Smith", "bob@university.edu");
    }
    
    @Test
    @DisplayName("Should add books to library successfully")
    void testAddBooks() {
        library.addBook(book1, 3);
        library.addBook(book2, 2);
        
        assertThat(library.isBookAvailable(book1.getIsbn())).isTrue();
        assertThat(library.isBookAvailable(book2.getIsbn())).isTrue();
    }
    
    @Test
    @DisplayName("Should search books by title, author, and category")
    void testSearchBooks() {
        library.addBook(book1, 1);
        library.addBook(book2, 1);
        library.addBook(book3, 1);
        
        // Search by title
        List<Book> javaBooks = library.searchBooks("Java");
        assertThat(javaBooks).hasSize(1);
        assertThat(javaBooks.get(0).getTitle()).isEqualTo("Effective Java");
        
        // Search by author
        List<Book> blochBooks = library.searchBooks("Bloch");
        assertThat(blochBooks).hasSize(1);
        assertThat(blochBooks.get(0).getAuthor()).isEqualTo("Joshua Bloch");
        
        // Search by category
        List<Book> programmingBooks = library.searchBooks("Programming");
        assertThat(programmingBooks).hasSize(3);
    }
    
    @Test
    @DisplayName("Should allow users to borrow available books")
    void testBorrowBooks() {
        library.addBook(book1, 2);
        
        boolean borrowed = library.borrowBook(book1.getIsbn(), student);
        
        assertThat(borrowed).isTrue();
        assertThat(student.getBorrowedBooks()).hasSize(1);
        assertThat(student.getBorrowedBooks().get(0).getBook()).isEqualTo(book1);
    }
    
    @Test
    @DisplayName("Should not allow borrowing unavailable books")
    void testBorrowUnavailableBook() {
        library.addBook(book1, 1);
        
        // First borrowing should succeed
        boolean firstBorrow = library.borrowBook(book1.getIsbn(), student);
        assertThat(firstBorrow).isTrue();
        
        // Second borrowing should fail (no copies available)
        boolean secondBorrow = library.borrowBook(book1.getIsbn(), faculty);
        assertThat(secondBorrow).isFalse();
    }
    
    @Test
    @DisplayName("Should not allow borrowing non-existent books")
    void testBorrowNonExistentBook() {
        boolean borrowed = library.borrowBook("invalid-isbn", student);
        assertThat(borrowed).isFalse();
    }
    
    @Test
    @DisplayName("Should enforce borrowing limits for students")
    void testStudentBorrowingLimit() {
        // Add 6 books
        for (int i = 0; i < 6; i++) {
            Book book = new Book("isbn-" + i, "Book " + i, "Author " + i, Arrays.asList("Category"));
            library.addBook(book, 1);
        }
        
        // Student should be able to borrow 5 books
        for (int i = 0; i < 5; i++) {
            boolean borrowed = library.borrowBook("isbn-" + i, student);
            assertThat(borrowed).isTrue();
        }
        
        // 6th book should fail due to limit
        boolean sixthBorrow = library.borrowBook("isbn-5", student);
        assertThat(sixthBorrow).isFalse();
        assertThat(student.getBorrowedBooks()).hasSize(5);
    }
    
    @Test
    @DisplayName("Should allow returning borrowed books")
    void testReturnBooks() {
        library.addBook(book1, 1);
        
        // Borrow the book
        library.borrowBook(book1.getIsbn(), student);
        assertThat(student.getBorrowedBooks()).hasSize(1);
        
        // Return the book
        boolean returned = library.returnBook(book1.getIsbn(), student);
        assertThat(returned).isTrue();
        assertThat(student.getBorrowedBooks()).isEmpty();
        assertThat(library.isBookAvailable(book1.getIsbn())).isTrue();
    }
    
    @Test
    @DisplayName("Should not allow returning books not borrowed by user")
    void testReturnNotBorrowedBook() {
        library.addBook(book1, 1);
        
        // Try to return without borrowing
        boolean returned = library.returnBook(book1.getIsbn(), student);
        assertThat(returned).isFalse();
    }
    
    @Test
    @DisplayName("Should calculate correct fine rates for different user types")
    void testFineCalculation() {
        // Create a borrow record that's overdue
        BorrowRecord studentRecord = new BorrowRecord(book1, student, LocalDate.now().minusDays(20));
        BorrowRecord facultyRecord = new BorrowRecord(book2, faculty, LocalDate.now().minusDays(40));
        
        // Student: 20 days borrowed - 14 days allowed = 6 days overdue * $0.50 = $3.00
        double studentFine = student.calculateFine(studentRecord);
        assertThat(studentFine).isEqualTo(3.00);
        
        // Faculty: 40 days borrowed - 30 days allowed = 10 days overdue * $1.00 = $10.00
        double facultyFine = faculty.calculateFine(facultyRecord);
        assertThat(facultyFine).isEqualTo(10.00);
    }
    
    @Test
    @DisplayName("Should not calculate fine for books returned on time")
    void testNoFineForOnTimeReturn() {
        BorrowRecord onTimeRecord = new BorrowRecord(book1, student, LocalDate.now().minusDays(10));
        
        double fine = student.calculateFine(onTimeRecord);
        assertThat(fine).isEqualTo(0.0);
    }
    
    @Test
    @DisplayName("Should handle concurrent borrowing operations safely")
    void testConcurrentBorrowing() {
        library.addBook(book1, 1);
        
        // Simulate concurrent access
        Thread thread1 = new Thread(() -> library.borrowBook(book1.getIsbn(), student));
        Thread thread2 = new Thread(() -> library.borrowBook(book1.getIsbn(), faculty));
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Only one user should have successfully borrowed the book
        int totalBorrowedBooks = student.getBorrowedBooks().size() + faculty.getBorrowedBooks().size();
        assertThat(totalBorrowedBooks).isEqualTo(1);
        assertThat(library.isBookAvailable(book1.getIsbn())).isFalse();
    }
    
    @Test
    @DisplayName("Should properly track book availability after multiple operations")
    void testBookAvailabilityTracking() {
        library.addBook(book1, 3);
        
        // Initial availability
        assertThat(library.isBookAvailable(book1.getIsbn())).isTrue();
        
        // Borrow 2 copies
        library.borrowBook(book1.getIsbn(), student);
        Student student2 = new Student("S002", "Bob", "bob@email.com");
        library.borrowBook(book1.getIsbn(), student2);
        
        // Still available (1 copy left)
        assertThat(library.isBookAvailable(book1.getIsbn())).isTrue();
        
        // Borrow last copy
        library.borrowBook(book1.getIsbn(), faculty);
        
        // No longer available
        assertThat(library.isBookAvailable(book1.getIsbn())).isFalse();
        
        // Return one copy
        library.returnBook(book1.getIsbn(), student);
        
        // Available again
        assertThat(library.isBookAvailable(book1.getIsbn())).isTrue();
    }
    
    @Test
    @DisplayName("Library system should manage multiple libraries")
    void testLibrarySystem() {
        LibrarySystem librarySystem = new LibrarySystem();
        
        Library library1 = new Library("Library 1", "Address 1");
        Library library2 = new Library("Library 2", "Address 2");
        
        librarySystem.addLibrary(library1);
        librarySystem.addLibrary(library2);
        librarySystem.addUser(student);
        
        library1.addBook(book1, 1);
        library2.addBook(book2, 1);
        
        // Search across all libraries
        List<Book> allBooks = librarySystem.searchBooksAcrossLibraries("Programming");
        assertThat(allBooks).hasSize(2);
        
        // Access specific library
        Library retrievedLibrary = librarySystem.getLibrary("Library 1");
        assertThat(retrievedLibrary).isNotNull();
        assertThat(retrievedLibrary.getName()).isEqualTo("Library 1");
        
        // Access specific user
        User retrievedUser = librarySystem.getUser("S001");
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("Alice Johnson");
    }
} 