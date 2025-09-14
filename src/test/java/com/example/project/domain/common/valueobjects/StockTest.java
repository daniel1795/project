package com.example.project.domain.common.valueobjects;

import com.example.project.domain.common.exceptions.InsufficientStockException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void of_WithPositiveQuantity_ShouldCreateStockWithAvailableTrue() {
        // When
        Stock stock = Stock.of(10);

        // Then
        assertEquals(10, stock.getQuantity());
        assertTrue(stock.getAvailable());
        assertTrue(stock.hasStock());
        assertTrue(stock.isAvailable());
    }

    @Test
    void of_WithZeroQuantity_ShouldCreateStockWithAvailableFalse() {
        // When
        Stock stock = Stock.of(0);

        // Then
        assertEquals(0, stock.getQuantity());
        assertFalse(stock.getAvailable());
        assertFalse(stock.hasStock());
        assertFalse(stock.isAvailable());
    }

    @Test
    void of_WithNegativeQuantity_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Stock.of(-1);
        });

        assertEquals("Stock quantity cannot be negative", exception.getMessage());
    }

    @Test
    void empty_ShouldCreateEmptyStock() {
        // When
        Stock stock = Stock.empty();

        // Then
        assertEquals(0, stock.getQuantity());
        assertFalse(stock.getAvailable());
        assertFalse(stock.hasStock());
        assertFalse(stock.isAvailable());
    }

    @Test
    void add_WithPositiveAmount_ShouldIncreaseStock() {
        // Given
        Stock stock = Stock.of(5);

        // When
        Stock newStock = stock.add(3);

        // Then
        assertEquals(8, newStock.getQuantity());
        assertTrue(newStock.getAvailable());
        assertTrue(newStock.hasStock());
        assertTrue(newStock.isAvailable());
    }

    @Test
    void add_WithZeroAmount_ShouldNotChangeStock() {
        // Given
        Stock stock = Stock.of(5);

        // When
        Stock newStock = stock.add(0);

        // Then
        assertEquals(5, newStock.getQuantity());
        assertTrue(newStock.getAvailable());
        assertTrue(newStock.hasStock());
        assertTrue(newStock.isAvailable());
    }

    @Test
    void add_WithNegativeAmount_ShouldThrowException() {
        // Given
        Stock stock = Stock.of(5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stock.add(-1);
        });

        assertEquals("Cannot add negative amount to stock", exception.getMessage());
    }

    @Test
    void reduce_WithValidAmount_ShouldDecreaseStock() {
        // Given
        Stock stock = Stock.of(10);

        // When
        Stock newStock = stock.reduce(3);

        // Then
        assertEquals(7, newStock.getQuantity());
        assertTrue(newStock.getAvailable());
        assertTrue(newStock.hasStock());
        assertTrue(newStock.isAvailable());
    }

    @Test
    void reduce_WithExactAmount_ShouldCreateEmptyStock() {
        // Given
        Stock stock = Stock.of(5);

        // When
        Stock newStock = stock.reduce(5);

        // Then
        assertEquals(0, newStock.getQuantity());
        assertFalse(newStock.getAvailable());
        assertFalse(newStock.hasStock());
        assertFalse(newStock.isAvailable());
    }

    @Test
    void reduce_WithAmountGreaterThanStock_ShouldThrowException() {
        // Given
        Stock stock = Stock.of(5);

        // When & Then
        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> {
            stock.reduce(10);
        });

        assertEquals("Insufficient stock. Requested: 10, Available: 5", exception.getMessage());
    }

    @Test
    void reduce_WithNegativeAmount_ShouldThrowException() {
        // Given
        Stock stock = Stock.of(5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stock.reduce(-1);
        });

        assertEquals("Cannot reduce negative amount from stock", exception.getMessage());
    }

    @Test
    void hasStock_WithPositiveQuantity_ShouldReturnTrue() {
        // Given
        Stock stock = Stock.of(1);

        // When & Then
        assertTrue(stock.hasStock());
    }

    @Test
    void hasStock_WithZeroQuantity_ShouldReturnFalse() {
        // Given
        Stock stock = Stock.of(0);

        // When & Then
        assertFalse(stock.hasStock());
    }

    @Test
    void isAvailable_WithPositiveQuantityAndAvailableTrue_ShouldReturnTrue() {
        // Given
        Stock stock = Stock.of(5);

        // When & Then
        assertTrue(stock.isAvailable());
    }

    @Test
    void isAvailable_WithZeroQuantity_ShouldReturnFalse() {
        // Given
        Stock stock = Stock.of(0);

        // When & Then
        assertFalse(stock.isAvailable());
    }

    @Test
    void equals_WithSameValues_ShouldReturnTrue() {
        // Given
        Stock stock1 = Stock.of(10);
        Stock stock2 = Stock.of(10);

        // When & Then
        assertEquals(stock1, stock2);
        assertEquals(stock1.hashCode(), stock2.hashCode());
    }

    @Test
    void equals_WithDifferentValues_ShouldReturnFalse() {
        // Given
        Stock stock1 = Stock.of(10);
        Stock stock2 = Stock.of(5);

        // When & Then
        assertNotEquals(stock1, stock2);
        assertNotEquals(stock1.hashCode(), stock2.hashCode());
    }

    @Test
    void equals_WithSameReference_ShouldReturnTrue() {
        // Given
        Stock stock = Stock.of(10);

        // When & Then
        assertEquals(stock, stock);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Given
        Stock stock = Stock.of(10);

        // When & Then
        assertNotEquals(stock, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Given
        Stock stock = Stock.of(10);

        // When & Then
        assertNotEquals(stock, "not a stock");
    }

    @Test
    void toString_ShouldContainQuantityAndAvailable() {
        // Given
        Stock stock = Stock.of(10);

        // When
        String toString = stock.toString();

        // Then
        assertTrue(toString.contains("quantity=10"));
        assertTrue(toString.contains("available=true"));
    }

    @Test
    void toString_WithEmptyStock_ShouldContainZeroQuantityAndFalseAvailable() {
        // Given
        Stock stock = Stock.empty();

        // When
        String toString = stock.toString();

        // Then
        assertTrue(toString.contains("quantity=0"));
        assertTrue(toString.contains("available=false"));
    }
}
