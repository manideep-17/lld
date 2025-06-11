package com.lld.practice.designpatterns.creational;

/**
 * Singleton Pattern - Ensures a class has only one instance and provides global access to it
 * 
 * Different implementations:
 * 1. Eager Initialization
 * 2. Lazy Initialization
 * 3. Thread-Safe Lazy Initialization
 * 4. Bill Pugh Singleton (Initialization-on-demand holder)
 * 5. Enum Singleton
 */
public class SingletonPattern {
    
    public static void main(String[] args) {
        // Test Eager Singleton
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        System.out.println("Eager Singleton - Same instance: " + (eager1 == eager2));
        
        // Test Lazy Singleton
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        System.out.println("Lazy Singleton - Same instance: " + (lazy1 == lazy2));
        
        // Test Thread-Safe Singleton
        ThreadSafeSingleton threadSafe1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton threadSafe2 = ThreadSafeSingleton.getInstance();
        System.out.println("Thread-Safe Singleton - Same instance: " + (threadSafe1 == threadSafe2));
        
        // Test Bill Pugh Singleton
        BillPughSingleton billPugh1 = BillPughSingleton.getInstance();
        BillPughSingleton billPugh2 = BillPughSingleton.getInstance();
        System.out.println("Bill Pugh Singleton - Same instance: " + (billPugh1 == billPugh2));
        
        // Test Enum Singleton
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE;
        EnumSingleton enumSingleton2 = EnumSingleton.INSTANCE;
        System.out.println("Enum Singleton - Same instance: " + (enumSingleton1 == enumSingleton2));
        
        // Real-world example
        DatabaseManager dbManager1 = DatabaseManager.getInstance();
        DatabaseManager dbManager2 = DatabaseManager.getInstance();
        System.out.println("DatabaseManager - Same instance: " + (dbManager1 == dbManager2));
        
        dbManager1.connect();
        dbManager1.executeQuery("SELECT * FROM users");
    }
}

// 1. Eager Initialization - Instance created at class loading time
class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();
    
    private EagerSingleton() {
        // Private constructor to prevent instantiation
    }
    
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}

// 2. Lazy Initialization - Instance created when first requested (not thread-safe)
class LazySingleton {
    private static LazySingleton instance;
    
    private LazySingleton() {}
    
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}

// 3. Thread-Safe Lazy Initialization - Synchronized method
class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    
    private ThreadSafeSingleton() {}
    
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}

// Better thread-safe version with double-checked locking
class ThreadSafeDoubleLockSingleton {
    private static volatile ThreadSafeDoubleLockSingleton instance;
    
    private ThreadSafeDoubleLockSingleton() {}
    
    public static ThreadSafeDoubleLockSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeDoubleLockSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeDoubleLockSingleton();
                }
            }
        }
        return instance;
    }
}

// 4. Bill Pugh Singleton - Uses inner static helper class
class BillPughSingleton {
    private BillPughSingleton() {}
    
    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }
    
    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}

// 5. Enum Singleton - Thread-safe and prevents reflection attacks
enum EnumSingleton {
    INSTANCE;
    
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

// Real-world example: Database Connection Manager
class DatabaseManager {
    private static volatile DatabaseManager instance;
    private boolean isConnected = false;
    
    private DatabaseManager() {
        // Initialize database connection
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }
    
    public void connect() {
        if (!isConnected) {
            System.out.println("Connecting to database...");
            isConnected = true;
        } else {
            System.out.println("Already connected to database");
        }
    }
    
    public void disconnect() {
        if (isConnected) {
            System.out.println("Disconnecting from database...");
            isConnected = false;
        }
    }
    
    public void executeQuery(String query) {
        if (isConnected) {
            System.out.println("Executing query: " + query);
        } else {
            System.out.println("Please connect to database first");
        }
    }
} 