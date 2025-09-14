package com.example.project.domain.common.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    void empty_ShouldCreateEmptyRating() {
        // When
        Rating rating = Rating.empty();

        // Then
        assertEquals(0.0, rating.getValue());
        assertEquals(0, rating.getCount());
        assertTrue(rating.isEmpty());
    }

    @Test
    void of_WithValidValues_ShouldCreateRating() {
        // When
        Rating rating = Rating.of(4.5, 10);

        // Then
        assertEquals(4.5, rating.getValue());
        assertEquals(10, rating.getCount());
        assertFalse(rating.isEmpty());
    }

    @Test
    void of_WithValueBelowZero_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Rating.of(-0.1, 10);
        });

        assertEquals("Rating must be between 0.0 and 5.0", exception.getMessage());
    }

    @Test
    void of_WithValueAboveFive_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Rating.of(5.1, 10);
        });

        assertEquals("Rating must be between 0.0 and 5.0", exception.getMessage());
    }

    @Test
    void of_WithNegativeCount_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Rating.of(4.5, -1);
        });

        assertEquals("Rating count cannot be negative", exception.getMessage());
    }

    @Test
    void of_WithZeroValueAndZeroCount_ShouldCreateValidRating() {
        // When
        Rating rating = Rating.of(0.0, 0);

        // Then
        assertEquals(0.0, rating.getValue());
        assertEquals(0, rating.getCount());
        assertTrue(rating.isEmpty());
    }

    @Test
    void of_WithFiveValue_ShouldCreateValidRating() {
        // When
        Rating rating = Rating.of(5.0, 1);

        // Then
        assertEquals(5.0, rating.getValue());
        assertEquals(1, rating.getCount());
        assertFalse(rating.isEmpty());
    }

    @Test
    void addRating_ToEmptyRating_ShouldCreateNewRating() {
        // Given
        Rating emptyRating = Rating.empty();

        // When
        Rating newRating = emptyRating.addRating(4.5);

        // Then
        assertEquals(4.5, newRating.getValue());
        assertEquals(1, newRating.getCount());
        assertFalse(newRating.isEmpty());
    }

    @Test
    void addRating_ToExistingRating_ShouldCalculateAverage() {
        // Given
        Rating existingRating = Rating.of(4.0, 2); // Average of 4.0 from 2 ratings

        // When
        Rating newRating = existingRating.addRating(5.0);

        // Then
        assertEquals(4.33, newRating.getValue(), 0.01); // (4.0*2 + 5.0) / 3 = 4.33
        assertEquals(3, newRating.getCount());
        assertFalse(newRating.isEmpty());
    }

    @Test
    void addRating_WithMultipleRatings_ShouldCalculateCorrectAverage() {
        // Given
        Rating rating = Rating.empty();

        // When
        rating = rating.addRating(5.0); // 5.0 (1 rating)
        rating = rating.addRating(4.0); // 4.5 (2 ratings)
        rating = rating.addRating(3.0); // 4.0 (3 ratings)

        // Then
        assertEquals(4.0, rating.getValue());
        assertEquals(3, rating.getCount());
    }

    @Test
    void addRating_WithInvalidValue_ShouldThrowException() {
        // Given
        Rating rating = Rating.of(4.0, 1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rating.addRating(-0.1);
        });

        assertEquals("Rating must be between 0.0 and 5.0", exception.getMessage());
    }

    @Test
    void addRating_WithValueAboveFive_ShouldThrowException() {
        // Given
        Rating rating = Rating.of(4.0, 1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rating.addRating(5.1);
        });

        assertEquals("Rating must be between 0.0 and 5.0", exception.getMessage());
    }

    @Test
    void addRating_WithZeroValue_ShouldWork() {
        // Given
        Rating rating = Rating.of(4.0, 1);

        // When
        Rating newRating = rating.addRating(0.0);

        // Then
        assertEquals(2.0, newRating.getValue()); // (4.0 + 0.0) / 2 = 2.0
        assertEquals(2, newRating.getCount());
    }

    @Test
    void addRating_WithFiveValue_ShouldWork() {
        // Given
        Rating rating = Rating.of(4.0, 1);

        // When
        Rating newRating = rating.addRating(5.0);

        // Then
        assertEquals(4.5, newRating.getValue()); // (4.0 + 5.0) / 2 = 4.5
        assertEquals(2, newRating.getCount());
    }

    @Test
    void isEmpty_WithZeroCount_ShouldReturnTrue() {
        // Given
        Rating rating = Rating.of(0.0, 0);

        // When & Then
        assertTrue(rating.isEmpty());
    }

    @Test
    void isEmpty_WithPositiveCount_ShouldReturnFalse() {
        // Given
        Rating rating = Rating.of(4.5, 1);

        // When & Then
        assertFalse(rating.isEmpty());
    }

    @Test
    void equals_WithSameValues_ShouldReturnTrue() {
        // Given
        Rating rating1 = Rating.of(4.5, 10);
        Rating rating2 = Rating.of(4.5, 10);

        // When & Then
        assertEquals(rating1, rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }

    @Test
    void equals_WithDifferentValues_ShouldReturnFalse() {
        // Given
        Rating rating1 = Rating.of(4.5, 10);
        Rating rating2 = Rating.of(4.0, 10);

        // When & Then
        assertNotEquals(rating1, rating2);
        assertNotEquals(rating1.hashCode(), rating2.hashCode());
    }

    @Test
    void equals_WithDifferentCounts_ShouldReturnFalse() {
        // Given
        Rating rating1 = Rating.of(4.5, 10);
        Rating rating2 = Rating.of(4.5, 5);

        // When & Then
        assertNotEquals(rating1, rating2);
        assertNotEquals(rating1.hashCode(), rating2.hashCode());
    }

    @Test
    void equals_WithSameReference_ShouldReturnTrue() {
        // Given
        Rating rating = Rating.of(4.5, 10);

        // When & Then
        assertEquals(rating, rating);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Given
        Rating rating = Rating.of(4.5, 10);

        // When & Then
        assertNotEquals(rating, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Given
        Rating rating = Rating.of(4.5, 10);

        // When & Then
        assertNotEquals(rating, "not a rating");
    }

    @Test
    void toString_ShouldContainValueAndCount() {
        // Given
        Rating rating = Rating.of(4.5, 10);

        // When
        String toString = rating.toString();

        // Then
        assertTrue(toString.contains("4.50"));
        assertTrue(toString.contains("10"));
    }

    @Test
    void toString_WithEmptyRating_ShouldContainZeroValues() {
        // Given
        Rating rating = Rating.empty();

        // When
        String toString = rating.toString();

        // Then
        assertTrue(toString.contains("0.00"));
        assertTrue(toString.contains("0"));
    }

    @Test
    void toString_WithDecimalValue_ShouldFormatCorrectly() {
        // Given
        Rating rating = Rating.of(4.33, 3);

        // When
        String toString = rating.toString();

        // Then
        assertTrue(toString.contains("4.33"));
        assertTrue(toString.contains("3"));
    }
}
