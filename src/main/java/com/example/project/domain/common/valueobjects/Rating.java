package com.example.project.domain.common.valueobjects;

import java.util.Objects;

public class Rating {
    
    private final double value;
    private final int count;
    
    private Rating(double value, int count) {
        if (value < 0.0 || value > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Rating count cannot be negative");
        }
        this.value = value;
        this.count = count;
    }
    
    public static Rating empty() {
        return new Rating(0.0, 0);
    }
    
    public static Rating of(double value, int count) {
        return new Rating(value, count);
    }
    
    public Rating addRating(double newRating) {
        if (newRating < 0.0 || newRating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        
        if (count == 0) {
            return new Rating(newRating, 1);
        }
        
        double totalRating = value * count + newRating;
        int newCount = count + 1;
        double newValue = totalRating / newCount;
        
        return new Rating(newValue, newCount);
    }
    
    public double getValue() {
        return value;
    }
    
    public int getCount() {
        return count;
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Double.compare(rating.value, value) == 0 && count == rating.count;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value, count);
    }
    
    @Override
    public String toString() {
        return String.format("Rating{value=%.2f, count=%d}", value, count);
    }
}
