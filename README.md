# Low-Level Design (LLD) Interview Practice Repository

This repository contains a comprehensive collection of Low-Level Design (LLD) implementations, covering key object-oriented design principles, design patterns, and system design problems for interview preparation.

## 🏗️ Project Structure

```
src/
├── main/java/com/lld/practice/
│   ├── basics/              # Java fundamentals and collections
│   ├── oop/                 # Object-Oriented Programming concepts
│   ├── solid/               # SOLID principles examples
│   ├── designpatterns/      # Gang of Four design patterns
│   │   ├── creational/      # Singleton, Factory, Builder, etc.
│   │   ├── structural/      # Adapter, Decorator, Facade, etc.
│   │   └── behavioral/      # Observer, Strategy, Command, etc.
│   └── questions/           # LLD interview questions and solutions
└── test/java/com/lld/practice/
    └── ...                  # Corresponding test files
```

## 🛠️ Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd lld
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Run specific examples**
   ```bash
   # Java basics
   mvn exec:java -Dexec.mainClass="com.lld.practice.basics.JavaBasicsExample"
   
   # OOP concepts
   mvn exec:java -Dexec.mainClass="com.lld.practice.oop.OOPExample"
   
   # SOLID principles
   mvn exec:java -Dexec.mainClass="com.lld.practice.solid.SOLIDPrinciplesExample"
   
   # Singleton pattern
   mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.creational.SingletonPattern"
   
   # Observer pattern
   mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.behavioral.ObserverPattern"
   
   # Library Management System
   mvn exec:java -Dexec.mainClass="com.lld.practice.questions.LibraryManagementSystem"
   ```

## 🚀 Quick Reference Commands

### Project Management Commands
```bash
# Clean and compile project
mvn clean compile

# Clean, compile and package
mvn clean package

# Compile only (incremental)
mvn compile

# Clean target directory
mvn clean
```

### Testing Commands
```bash
# Run all tests
mvn test

# Run all tests with verbose output
mvn test -Dtest=*

# Run specific test class
mvn test -Dtest=LibraryManagementSystemTest

# Run specific test method
mvn test -Dtest=LibraryManagementSystemTest#testBorrowBooks

# Run tests with quiet output (less verbose)
mvn test -q

# Skip tests during build
mvn package -DskipTests
```

### Execution Commands

#### Java Fundamentals
```bash
# Java Basics - Collections, Streams, Lambda
mvn exec:java -Dexec.mainClass="com.lld.practice.basics.JavaBasicsExample"

# Java Basics with quiet output
mvn exec:java -Dexec.mainClass="com.lld.practice.basics.JavaBasicsExample" -q
```

#### Object-Oriented Programming
```bash
# OOP Concepts - Inheritance, Polymorphism, Encapsulation, Abstraction
mvn exec:java -Dexec.mainClass="com.lld.practice.oop.OOPExample"

# OOP with quiet output
mvn exec:java -Dexec.mainClass="com.lld.practice.oop.OOPExample" -q
```

#### SOLID Principles
```bash
# All SOLID Principles examples
mvn exec:java -Dexec.mainClass="com.lld.practice.solid.SOLIDPrinciplesExample"

# SOLID with quiet output
mvn exec:java -Dexec.mainClass="com.lld.practice.solid.SOLIDPrinciplesExample" -q
```

#### Design Patterns - Creational
```bash
# Singleton Pattern (5 different implementations)
mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.creational.SingletonPattern"

# Singleton with quiet output
mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.creational.SingletonPattern" -q
```

#### Design Patterns - Behavioral
```bash
# Observer Pattern (Weather Station example)
mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.behavioral.ObserverPattern"

# Observer with quiet output
mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.behavioral.ObserverPattern" -q
```

#### LLD Interview Questions
```bash
# Library Management System
mvn exec:java -Dexec.mainClass="com.lld.practice.questions.LibraryManagementSystem"

# Library Management System with quiet output
mvn exec:java -Dexec.mainClass="com.lld.practice.questions.LibraryManagementSystem" -q
```

### Development Commands
```bash
# Generate project in IDE-friendly format
mvn idea:idea          # For IntelliJ IDEA
mvn eclipse:eclipse    # For Eclipse

# Format code (if you have a formatter plugin)
mvn spotless:apply

# Check code style (if you have checkstyle plugin)
mvn checkstyle:check

# Generate dependency tree
mvn dependency:tree

# Show project information
mvn help:describe -Dplugin=exec
```

### Useful Shortcuts
```bash
# Run all examples in sequence (copy-paste friendly)
mvn exec:java -Dexec.mainClass="com.lld.practice.basics.JavaBasicsExample" -q && \
mvn exec:java -Dexec.mainClass="com.lld.practice.oop.OOPExample" -q && \
mvn exec:java -Dexec.mainClass="com.lld.practice.solid.SOLIDPrinciplesExample" -q && \
mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.creational.SingletonPattern" -q && \
mvn exec:java -Dexec.mainClass="com.lld.practice.designpatterns.behavioral.ObserverPattern" -q && \
mvn exec:java -Dexec.mainClass="com.lld.practice.questions.LibraryManagementSystem" -q

# Quick test and run cycle
mvn test -q && mvn exec:java -Dexec.mainClass="com.lld.practice.questions.LibraryManagementSystem" -q

# Build and test everything
mvn clean compile test -q
```

## 📚 Content Overview

### 1. Java Basics (`com.lld.practice.basics`)
- **Collections Framework**: ArrayList, HashMap, TreeMap, etc.
- **Streams API**: Filtering, mapping, collecting operations
- **Lambda Expressions**: Functional programming concepts
- **Generics**: Type safety and parameterized types

### 2. Object-Oriented Programming (`com.lld.practice.oop`)
- **Inheritance**: Class hierarchies and method overriding
- **Polymorphism**: Runtime method dispatch
- **Encapsulation**: Data hiding and access modifiers
- **Abstraction**: Abstract classes and interfaces

### 3. SOLID Principles (`com.lld.practice.solid`)
- **S** - Single Responsibility Principle
- **O** - Open/Closed Principle
- **L** - Liskov Substitution Principle
- **I** - Interface Segregation Principle
- **D** - Dependency Inversion Principle

### 4. Design Patterns (`com.lld.practice.designpatterns`)

#### Creational Patterns
- **Singleton**: Ensure only one instance exists
- **Factory Method**: Create objects without specifying exact classes
- **Builder**: Construct complex objects step by step
- **Prototype**: Clone existing objects

#### Structural Patterns
- **Adapter**: Make incompatible interfaces work together
- **Decorator**: Add behavior to objects dynamically
- **Facade**: Provide simplified interface to complex subsystem
- **Composite**: Treat individual objects and compositions uniformly

#### Behavioral Patterns
- **Observer**: Define one-to-many dependency between objects
- **Strategy**: Define family of algorithms and make them interchangeable
- **Command**: Encapsulate requests as objects
- **State**: Change object behavior when internal state changes

### 5. LLD Interview Questions (`com.lld.practice.questions`)
- **Library Management System**: Complete implementation with borrowing, returning, fines
- **Parking Lot System**: Multi-level parking with different vehicle types
- **Chat Application**: Real-time messaging with user management
- **Food Delivery System**: Order management and delivery tracking
- **Movie Booking System**: Theater management and seat reservation

## 🧪 Testing

The project includes comprehensive unit tests using JUnit 5 and AssertJ for fluent assertions.

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LibraryManagementSystemTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Structure
- Each main class has corresponding test class
- Tests cover both positive and negative scenarios
- Edge cases and error conditions are thoroughly tested
- Concurrent operations are tested where applicable

## 🎯 How to Use This Repository

### For Interview Preparation

1. **Start with Basics**: Review Java fundamentals and OOP concepts
2. **Master SOLID Principles**: Understand and practice each principle
3. **Learn Design Patterns**: Implement patterns from scratch
4. **Practice LLD Questions**: Solve system design problems

### Study Approach

1. **Read the Code**: Understand the implementation
2. **Run Examples**: Execute the main methods to see output
3. **Modify and Experiment**: Change code to see different behaviors
4. **Write Tests**: Practice TDD by writing tests first
5. **Implement Variations**: Try different approaches to same problems

### Common Interview Topics Covered

- **System Design**: Library, Parking Lot, Chat systems
- **Design Patterns**: All major GoF patterns
- **Concurrency**: Thread-safe implementations
- **Data Structures**: Proper use of Collections
- **Error Handling**: Robust exception management
- **Testing**: Unit testing best practices

## 📝 Contributing

Feel free to contribute by:
- Adding new LLD questions and solutions
- Implementing additional design patterns
- Improving existing code with better practices
- Adding more comprehensive tests
- Updating documentation

## 🔧 IDE Configuration

### IntelliJ IDEA
1. Import as Maven project
2. Set Project SDK to Java 17+
3. Enable annotation processing
4. Configure code style (Google Java Style recommended)

### VS Code
1. Install Java Extension Pack
2. Open folder in VS Code
3. Maven will be auto-detected
4. Use Command Palette for Maven commands

## 📖 Additional Resources

### Books
- "Effective Java" by Joshua Bloch
- "Design Patterns" by Gang of Four
- "Clean Code" by Robert Martin
- "Head First Design Patterns"

### Online Resources
- [Refactoring Guru - Design Patterns](https://refactoring.guru/design-patterns)
- [Java Documentation](https://docs.oracle.com/en/java/)
- [Baeldung Java Tutorials](https://www.baeldung.com/)

## 🚀 Next Steps

1. **Complete all examples**: Go through each package systematically
2. **Practice coding**: Implement patterns without looking at solutions
3. **Mock interviews**: Practice explaining your design decisions
4. **Build projects**: Create your own LLD problems and solutions
5. **Stay updated**: Keep learning new patterns and best practices

---

**Happy Coding and Good Luck with Your Interviews! 🎉**
