package com.lld.practice.designpatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern - Defines a one-to-many dependency between objects
 * When one object changes state, all dependents are notified automatically
 */
public class ObserverPattern {
    
    public static void main(String[] args) {
        // Create subjects
        WeatherStation weatherStation = new WeatherStation();
        NewsAgency newsAgency = new NewsAgency();
        
        // Create observers
        PhoneDisplay phoneDisplay = new PhoneDisplay("iPhone");
        TabletDisplay tabletDisplay = new TabletDisplay("iPad");
        LaptopDisplay laptopDisplay = new LaptopDisplay("MacBook");
        
        NewsChannel cnn = new NewsChannel("CNN");
        NewsChannel bbc = new NewsChannel("BBC");
        
        // Register observers
        weatherStation.addObserver(phoneDisplay);
        weatherStation.addObserver(tabletDisplay);
        weatherStation.addObserver(laptopDisplay);
        
        newsAgency.subscribe(cnn);
        newsAgency.subscribe(bbc);
        
        // Update subjects - observers will be notified automatically
        System.out.println("=== Weather Updates ===");
        weatherStation.setWeatherData(25.5f, 65, 1013.25f);
        
        System.out.println("\n=== News Updates ===");
        newsAgency.setNews("Breaking: New technology breakthrough announced!");
        
        System.out.println("\n=== Removing an observer ===");
        weatherStation.removeObserver(tabletDisplay);
        weatherStation.setWeatherData(28.0f, 70, 1015.0f);
    }
}

// Observer interface
interface WeatherObserver {
    void update(float temperature, int humidity, float pressure);
}

// Subject interface
interface WeatherSubject {
    void addObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers();
}

// Concrete Subject - Weather Station
class WeatherStation implements WeatherSubject {
    private List<WeatherObserver> observers;
    private float temperature;
    private int humidity;
    private float pressure;
    
    public WeatherStation() {
        observers = new ArrayList<>();
    }
    
    @Override
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
        System.out.println("Observer added: " + observer.getClass().getSimpleName());
    }
    
    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }
    
    @Override
    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    public void setWeatherData(float temperature, int humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}

// Concrete Observers - Different display devices
class PhoneDisplay implements WeatherObserver {
    private String deviceName;
    
    public PhoneDisplay(String deviceName) {
        this.deviceName = deviceName;
    }
    
    @Override
    public void update(float temperature, int humidity, float pressure) {
        System.out.println(deviceName + " - Weather Update: " + 
                          temperature + "°C, " + humidity + "%, " + pressure + " hPa");
    }
}

class TabletDisplay implements WeatherObserver {
    private String deviceName;
    
    public TabletDisplay(String deviceName) {
        this.deviceName = deviceName;
    }
    
    @Override
    public void update(float temperature, int humidity, float pressure) {
        System.out.println(deviceName + " - Detailed Weather: Temperature=" + temperature + 
                          "°C, Humidity=" + humidity + "%, Pressure=" + pressure + " hPa");
    }
}

class LaptopDisplay implements WeatherObserver {
    private String deviceName;
    
    public LaptopDisplay(String deviceName) {
        this.deviceName = deviceName;
    }
    
    @Override
    public void update(float temperature, int humidity, float pressure) {
        System.out.println(deviceName + " - Weather Dashboard Updated: " +
                          "T:" + temperature + "°C | H:" + humidity + "% | P:" + pressure + "hPa");
    }
}

// Another example with News Agency and News Channels
interface NewsObserver {
    void update(String news);
}

class NewsAgency {
    private List<NewsObserver> observers = new ArrayList<>();
    private String news;
    
    public void subscribe(NewsObserver observer) {
        observers.add(observer);
    }
    
    public void unsubscribe(NewsObserver observer) {
        observers.remove(observer);
    }
    
    public void setNews(String news) {
        this.news = news;
        notifyAllObservers();
    }
    
    private void notifyAllObservers() {
        for (NewsObserver observer : observers) {
            observer.update(news);
        }
    }
}

class NewsChannel implements NewsObserver {
    private String channelName;
    
    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(String news) {
        System.out.println(channelName + " broadcasting: " + news);
    }
}

// Real-world example: Stock Price Observer
class Stock {
    private List<StockObserver> observers = new ArrayList<>();
    private String symbol;
    private double price;
    
    public Stock(String symbol) {
        this.symbol = symbol;
    }
    
    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }
    
    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(symbol, price);
        }
    }
}

interface StockObserver {
    void update(String symbol, double price);
}

class Investor implements StockObserver {
    private String name;
    
    public Investor(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String symbol, double price) {
        System.out.println(name + " notified: " + symbol + " is now $" + price);
    }
} 