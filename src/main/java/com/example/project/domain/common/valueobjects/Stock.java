package com.example.project.domain.common.valueobjects;

import com.example.project.domain.common.exceptions.InsufficientStockException;
import java.util.Objects;

public class Stock {
    
    private final int quantity;
    private final boolean available;
    
    private Stock(int quantity, boolean available) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.quantity = quantity;
        this.available = available;
    }
    
    public static Stock of(int quantity) {
        return new Stock(quantity, quantity > 0);
    }
    
    public static Stock empty() {
        return new Stock(0, false);
    }
    
    public Stock add(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative amount to stock");
        }
        int newQuantity = quantity + amount;
        return new Stock(newQuantity, newQuantity > 0);
    }
    
    public Stock reduce(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot reduce negative amount from stock");
        }
        if (amount > quantity) {
            throw new InsufficientStockException(
                String.format("Insufficient stock. Requested: %d, Available: %d", amount, quantity)
            );
        }
        int newQuantity = quantity - amount;
        return new Stock(newQuantity, newQuantity > 0);
    }
    
    public boolean hasStock() {
        return quantity > 0;
    }
    
    public boolean isAvailable() {
        return available && quantity > 0;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public boolean getAvailable() {
        return available;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity && available == stock.available;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(quantity, available);
    }
    
    @Override
    public String toString() {
        return String.format("Stock{quantity=%d, available=%s}", quantity, available);
    }
}
