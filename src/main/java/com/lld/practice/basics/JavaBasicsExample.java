package com.lld.practice.basics;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Java Basics Examples - Collections, Streams, Lambda expressions
 */
public class JavaBasicsExample {
    
    public static void main(String[] args) {
        JavaBasicsExample example = new JavaBasicsExample();
        example.demonstrateCollections();
        example.demonstrateStreams();
        example.demonstrateLambdas();
    }
    
    /**
     * Demonstrates various Collection interfaces and implementations
     */
    public void demonstrateCollections() {
        System.out.println("=== Collections Demo ===");
        
        // List - maintains order, allows duplicates
        List<String> list = new ArrayList<>(Arrays.asList("apple", "banana", "apple", "cherry"));
        System.out.println("List: " + list);
        
        // Set - no duplicates
        Set<String> set = new HashSet<>(list);
        System.out.println("Set: " + set);
        
        // Map - key-value pairs
        Map<String, Integer> fruitCounts = new HashMap<>();
        for (String fruit : list) {
            fruitCounts.put(fruit, fruitCounts.getOrDefault(fruit, 0) + 1);
        }
        System.out.println("Map: " + fruitCounts);
        
        // LinkedHashMap - maintains insertion order
        Map<String, Integer> orderedMap = new LinkedHashMap<>(fruitCounts);
        System.out.println("LinkedHashMap: " + orderedMap);
        
        // TreeMap - sorted order
        Map<String, Integer> sortedMap = new TreeMap<>(fruitCounts);
        System.out.println("TreeMap: " + sortedMap);
    }
    
    /**
     * Demonstrates Java 8 Streams API
     */
    public void demonstrateStreams() {
        System.out.println("\n=== Streams Demo ===");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Filter even numbers
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers);
        
        // Square and sum
        int sumOfSquares = numbers.stream()
                .mapToInt(n -> n * n)
                .sum();
        System.out.println("Sum of squares: " + sumOfSquares);
        
        // Group by even/odd
        Map<Boolean, List<Integer>> groupedByEvenOdd = numbers.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0));
        System.out.println("Grouped by even/odd: " + groupedByEvenOdd);
    }
    
    /**
     * Demonstrates Lambda expressions and Functional interfaces
     */
    public void demonstrateLambdas() {
        System.out.println("\n=== Lambda Demo ===");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        
        // Using lambda with forEach
        System.out.print("Names: ");
        names.forEach(name -> System.out.print(name + " "));
        System.out.println();
        
        // Custom functional interface
        MathOperation addition = (a, b) -> a + b;
        MathOperation multiplication = (a, b) -> a * b;
        
        System.out.println("Addition: " + operate(5, 3, addition));
        System.out.println("Multiplication: " + operate(5, 3, multiplication));
        
        // Method reference
        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Uppercase names: " + upperCaseNames);
    }
    
    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
    
    @FunctionalInterface
    interface MathOperation {
        int operation(int a, int b);
    }
} 