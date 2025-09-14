package com.example.project.infraestructure.dto;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDtoTest {

    private Validator validator;
    private ProductRequestDto validProductRequest;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        setupValidProductRequest();
    }

    private void setupValidProductRequest() {
        validProductRequest = ProductRequestDto.builder()
                .title("iPhone 15 Pro Max")
                .description("Smartphone Apple con las últimas características")
                .price(new BigDecimal("1299.99"))
                .currency("USD")
                .availableQuantity(50)
                .status(ProductStatus.ACTIVE)
                .category("Electrónicos")
                .subcategory("Smartphones")
                .sellerId("550e8400-e29b-41d4-a716-446655440000")
                .sellerName("TechStore Pro")
                .listingType(ListingType.MERCADO_LIBRE)
                .freeShipping(true)
                .shippingCost(new BigDecimal("15.99"))
                .weight(0.5)
                .width(15.0)
                .height(20.0)
                .length(8.0)
                .images(Arrays.asList("https://example.com/image1.jpg"))
                .attributes(Arrays.asList("Color: Azul", "Memoria: 256GB"))
                .condition(ProductCondition.NEW)
                .brand("Apple")
                .model("iPhone 15 Pro Max")
                .stock(25)
                .available(true)
                .sku("IPH15PM-256-NT")
                .barcode("1234567890123")
                .tags(Arrays.asList("smartphone", "apple", "premium"))
                .warranty("1 año de garantía del fabricante")
                .returnPolicy("30 días para devolución")
                .build();
    }

    @Test
    void validProductRequest_ShouldHaveNoViolations() {
        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertTrue(violations.isEmpty(), "Valid product request should have no violations");
    }

    @Test
    void title_WhenBlank_ShouldHaveViolation() {
        // Given
        validProductRequest.setTitle("");

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("title") &&
                        v.getMessage().contains("obligatorio")));
    }

    @Test
    void title_WhenNull_ShouldHaveViolation() {
        // Given
        validProductRequest.setTitle(null);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("title") &&
                        v.getMessage().contains("obligatorio")));
    }

    @Test
    void title_WhenTooLong_ShouldHaveViolation() {
        // Given
        validProductRequest.setTitle("a".repeat(101)); // Excede 100 caracteres

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("title") &&
                        v.getMessage().contains("100 caracteres")));
    }

    @Test
    void description_WhenBlank_ShouldHaveViolation() {
        // Given
        validProductRequest.setDescription("");

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description") &&
                        v.getMessage().contains("obligatoria")));
    }

    @Test
    void description_WhenTooLong_ShouldHaveViolation() {
        // Given
        validProductRequest.setDescription("a".repeat(2001)); // Excede 2000 caracteres

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description") &&
                        v.getMessage().contains("2000 caracteres")));
    }

    @Test
    void price_WhenNull_ShouldHaveViolation() {
        // Given
        validProductRequest.setPrice(null);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("price") &&
                        v.getMessage().contains("obligatorio")));
    }

    @Test
    void price_WhenZero_ShouldHaveViolation() {
        // Given
        validProductRequest.setPrice(BigDecimal.ZERO);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("price") &&
                        v.getMessage().contains("mayor a 0")));
    }

    @Test
    void price_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setPrice(new BigDecimal("-100.00"));

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("price") &&
                        v.getMessage().contains("mayor a 0")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ARS", "USD", "BRL", "MXN", "COP", "CLP", "UYU", "PEN"})
    void currency_WithValidValues_ShouldHaveNoViolation(String validCurrency) {
        // Given
        validProductRequest.setCurrency(validCurrency);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertTrue(violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().equals("currency")),
                "Currency " + validCurrency + " should be valid");
    }

    @Test
    void currency_WithInvalidValue_ShouldHaveViolation() {
        // Given
        validProductRequest.setCurrency("INVALID");

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("currency") &&
                        v.getMessage().contains("ARS, USD, BRL, MXN, COP, CLP, UYU, PEN")));
    }

    @Test
    void availableQuantity_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setAvailableQuantity(-1);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("availableQuantity") &&
                        v.getMessage().contains("negativa")));
    }

    @Test
    void weight_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setWeight(-1.0);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("weight") &&
                        v.getMessage().contains("negativo")));
    }

    @Test
    void width_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setWidth(-1.0);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("width") &&
                        v.getMessage().contains("negativo")));
    }

    @Test
    void height_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setHeight(-1.0);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("height") &&
                        v.getMessage().contains("negativa")));
    }

    @Test
    void length_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setLength(-1.0);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("length") &&
                        v.getMessage().contains("negativa")));
    }

    @Test
    void stock_WhenNegative_ShouldHaveViolation() {
        // Given
        validProductRequest.setStock(-1);

        // When
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(validProductRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("stock") &&
                        v.getMessage().contains("negativo")));
    }

    @Test
    void builder_ShouldCreateValidObject() {
        // When
        ProductRequestDto product = ProductRequestDto.builder()
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .currency("USD")
                .availableQuantity(10)
                .status(ProductStatus.ACTIVE)
                .category("Test Category")
                .subcategory("Test Subcategory")
                .sellerId("test-seller-id")
                .sellerName("Test Seller")
                .freeShipping(true)
                .condition(ProductCondition.NEW)
                .available(true)
                .build();

        // Then
        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals(10, product.getAvailableQuantity());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        assertEquals("Test Category", product.getCategory());
        assertEquals("Test Subcategory", product.getSubcategory());
        assertEquals("test-seller-id", product.getSellerId());
        assertEquals("Test Seller", product.getSellerName());
        assertTrue(product.getFreeShipping());
        assertEquals(ProductCondition.NEW, product.getCondition());
        assertTrue(product.getAvailable());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        // Given
        ProductRequestDto product1 = ProductRequestDto.builder()
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .currency("USD")
                .availableQuantity(10)
                .status(ProductStatus.ACTIVE)
                .category("Test Category")
                .subcategory("Test Subcategory")
                .sellerId("test-seller-id")
                .sellerName("Test Seller")
                .freeShipping(true)
                .condition(ProductCondition.NEW)
                .available(true)
                .build();

        ProductRequestDto product2 = ProductRequestDto.builder()
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .currency("USD")
                .availableQuantity(10)
                .status(ProductStatus.ACTIVE)
                .category("Test Category")
                .subcategory("Test Subcategory")
                .sellerId("test-seller-id")
                .sellerName("Test Seller")
                .freeShipping(true)
                .condition(ProductCondition.NEW)
                .available(true)
                .build();

        // When & Then
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }
}
