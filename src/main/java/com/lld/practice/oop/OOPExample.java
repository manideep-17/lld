package com.lld.practice.oop;

/**
 * OOP Concepts Example - Inheritance, Polymorphism, Encapsulation, Abstraction
 */
public class OOPExample {
    
    public static void main(String[] args) {
        // Polymorphism in action
        Animal[] animals = {
            new Dog("Buddy", 3),
            new Cat("Whiskers", 2),
            new Bird("Tweety", 1)
        };
        
        for (Animal animal : animals) {
            animal.makeSound();  // Polymorphism
            animal.displayInfo();
            
            // Instanceof check for specific behavior
            if (animal instanceof Dog) {
                ((Dog) animal).wagTail();
            } else if (animal instanceof Cat) {
                ((Cat) animal).purr();
            } else if (animal instanceof Bird) {
                ((Bird) animal).fly();
            }
            System.out.println();
        }
        
        // Abstract class usage
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0)
        };
        
        for (Shape shape : shapes) {
            System.out.println(shape.getShapeType() + " - Area: " + shape.calculateArea());
        }
    }
}

// Abstract class demonstrating abstraction
abstract class Animal {
    protected String name;  // Encapsulation - protected access
    private int age;        // Encapsulation - private access
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void makeSound();
    
    // Concrete method
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
    
    // Getter for encapsulated field
    public int getAge() {
        return age;
    }
    
    // Setter for encapsulated field
    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }
}

// Inheritance - Dog extends Animal
class Dog extends Animal {
    
    public Dog(String name, int age) {
        super(name, age);  // Call to parent constructor
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
    
    // Specific method for Dog
    public void wagTail() {
        System.out.println(name + " is wagging tail!");
    }
}

// Inheritance - Cat extends Animal
class Cat extends Animal {
    
    public Cat(String name, int age) {
        super(name, age);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow! Meow!");
    }
    
    // Specific method for Cat
    public void purr() {
        System.out.println(name + " is purring...");
    }
}

// Inheritance - Bird extends Animal
class Bird extends Animal {
    
    public Bird(String name, int age) {
        super(name, age);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Tweet! Tweet!");
    }
    
    // Specific method for Bird
    public void fly() {
        System.out.println(name + " is flying high!");
    }
}

// Another abstract class example
abstract class Shape {
    protected String shapeType;
    
    public Shape(String shapeType) {
        this.shapeType = shapeType;
    }
    
    // Abstract method
    public abstract double calculateArea();
    
    // Concrete method
    public String getShapeType() {
        return shapeType;
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        super("Rectangle");
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
} 