package com.lld.practice.solid;

import java.util.List;
import java.util.ArrayList;

/**
 * SOLID Principles Example
 * S - Single Responsibility Principle
 * O - Open/Closed Principle
 * L - Liskov Substitution Principle
 * I - Interface Segregation Principle
 * D - Dependency Inversion Principle
 */
public class SOLIDPrinciplesExample {
    
    public static void main(String[] args) {
        // Example usage
        Employee emp = new Employee("John Doe", 50000);
        
        // SRP: Separate responsibilities
        EmployeeValidator validator = new EmployeeValidator();
        EmployeePersistence persistence = new EmployeePersistence();
        EmployeeReportGenerator reportGenerator = new EmployeeReportGenerator();
        
        if (validator.isValid(emp)) {
            persistence.save(emp);
            String report = reportGenerator.generateReport(emp);
            System.out.println(report);
        }
        
        // OCP: Different shape calculations without modifying existing code
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new CircleShape(5));
        shapes.add(new RectangleShape(4, 6));
        shapes.add(new TriangleShape(3, 4));
        
        AreaCalculator calculator = new AreaCalculator();
        double totalArea = calculator.calculateTotalArea(shapes);
        System.out.println("Total area: " + totalArea);
        
        // DIP: High-level modules depend on abstractions
        DatabaseConnection dbConnection = new MySQLConnection();
        UserRepository userRepository = new UserRepository(dbConnection);
        userRepository.saveUser(new User("Alice"));
    }
}

// ============ SINGLE RESPONSIBILITY PRINCIPLE (SRP) ============
// Each class has only one reason to change

class Employee {
    private String name;
    private double salary;
    
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}

// Separate class for validation logic
class EmployeeValidator {
    public boolean isValid(Employee employee) {
        return employee.getName() != null && 
               employee.getName().length() > 0 && 
               employee.getSalary() > 0;
    }
}

// Separate class for persistence logic
class EmployeePersistence {
    public void save(Employee employee) {
        System.out.println("Saving employee: " + employee.getName());
        // Database save logic here
    }
}

// Separate class for report generation
class EmployeeReportGenerator {
    public String generateReport(Employee employee) {
        return "Employee Report: " + employee.getName() + " - $" + employee.getSalary();
    }
}

// ============ OPEN/CLOSED PRINCIPLE (OCP) ============
// Open for extension, closed for modification

interface Shape {
    double calculateArea();
}

class CircleShape implements Shape {
    private double radius;
    
    public CircleShape(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class RectangleShape implements Shape {
    private double length;
    private double width;
    
    public RectangleShape(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
}

// Adding new shape without modifying existing code
class TriangleShape implements Shape {
    private double base;
    private double height;
    
    public TriangleShape(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

class AreaCalculator {
    public double calculateTotalArea(List<Shape> shapes) {
        return shapes.stream().mapToDouble(Shape::calculateArea).sum();
    }
}

// ============ LISKOV SUBSTITUTION PRINCIPLE (LSP) ============
// Derived classes must be substitutable for their base classes

abstract class Bird {
    public abstract void eat();
    public abstract void makeSound();
}

// Flying birds
abstract class FlyingBird extends Bird {
    public abstract void fly();
}

class Sparrow extends FlyingBird {
    @Override
    public void eat() {
        System.out.println("Sparrow is eating seeds");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Sparrow chirps");
    }
    
    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

class Eagle extends FlyingBird {
    @Override
    public void eat() {
        System.out.println("Eagle is hunting");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Eagle screeches");
    }
    
    @Override
    public void fly() {
        System.out.println("Eagle soars high");
    }
}

// Non-flying birds
class Penguin extends Bird {
    @Override
    public void eat() {
        System.out.println("Penguin is eating fish");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Penguin makes noise");
    }
    
    // Penguin doesn't extend FlyingBird, so no fly() method
    public void swim() {
        System.out.println("Penguin is swimming");
    }
}

// ============ INTERFACE SEGREGATION PRINCIPLE (ISP) ============
// Clients should not be forced to depend on interfaces they don't use

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

class Human implements Workable, Eatable, Sleepable {
    @Override
    public void work() {
        System.out.println("Human is working");
    }
    
    @Override
    public void eat() {
        System.out.println("Human is eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Human is sleeping");
    }
}

class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }
    // Robot doesn't need to eat or sleep
}

// ============ DEPENDENCY INVERSION PRINCIPLE (DIP) ============
// High-level modules should not depend on low-level modules
// Both should depend on abstractions

interface DatabaseConnection {
    void connect();
    void save(String data);
}

class MySQLConnection implements DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database");
    }
    
    @Override
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class PostgreSQLConnection implements DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connecting to PostgreSQL database");
    }
    
    @Override
    public void save(String data) {
        System.out.println("Saving to PostgreSQL: " + data);
    }
}

class User {
    private String name;
    
    public User(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

// High-level module depends on abstraction, not concrete implementation
class UserRepository {
    private DatabaseConnection dbConnection;
    
    public UserRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    public void saveUser(User user) {
        dbConnection.connect();
        dbConnection.save(user.getName());
    }
} 