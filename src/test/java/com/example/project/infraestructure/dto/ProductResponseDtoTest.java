package com.example.project.infraestructure.dto;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseDtoTest {

    private ProductResponseDto validProductResponse;

    @BeforeEach
    void setUp() {
        setupValidProductResponse();
    }

    private void setupValidProductResponse() {
        validProductResponse = ProductResponseDto.builder()
                .id("f47ac10b-58cc-4372-a567-0e02b2c3d479")
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
                .shippingCost(new BigDecimal("0.00"))
                .weight(0.5)
                .width(15.0)
                .height(20.0)
                .length(8.0)
                .images(Arrays.asList("https://example.com/image1.jpg", "https://example.com/image2.jpg"))
                .attributes(Arrays.asList("Color: Azul", "Memoria: 256GB", "Procesador: A17 Pro"))
                .views(1250)
                .sales(45)
                .rating(4.5)
                .ratingCount(128)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastSoldAt(LocalDateTime.now())
                .condition(ProductCondition.NEW)
                .brand("Apple")
                .model("iPhone 15 Pro Max")
                .stock(25)
                .available(true)
                .sku("IPH15PM-256-NT")
                .barcode("1234567890123")
                .tags(Arrays.asList("smartphone", "apple", "premium", "5g"))
                .warranty("1 año de garantía del fabricante")
                .returnPolicy("30 días para devolución")
                .isAvailable(true)
                .hasStock(true)
                .isFreeShipping(true)
                .build();
    }

    @Test
    void builder_ShouldCreateValidObject() {
        // When
        ProductResponseDto product = ProductResponseDto.builder()
                .id("test-id")
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
                .listingType(ListingType.MERCADO_LIBRE)
                .freeShipping(true)
                .shippingCost(new BigDecimal("5.99"))
                .weight(0.3)
                .width(10.0)
                .height(15.0)
                .length(5.0)
                .images(Arrays.asList("https://example.com/test.jpg"))
                .attributes(Arrays.asList("Color: Red"))
                .views(100)
                .sales(5)
                .rating(4.0)
                .ratingCount(10)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastSoldAt(LocalDateTime.now())
                .condition(ProductCondition.NEW)
                .brand("Test Brand")
                .model("Test Model")
                .stock(10)
                .available(true)
                .sku("TEST-SKU")
                .barcode("9876543210987")
                .tags(Arrays.asList("test", "product"))
                .warranty("6 months warranty")
                .returnPolicy("14 days return")
                .isAvailable(true)
                .hasStock(true)
                .isFreeShipping(false)
                .build();

        // Then
        assertNotNull(product);
        assertEquals("test-id", product.getId());
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
        assertEquals(ListingType.MERCADO_LIBRE, product.getListingType());
        assertTrue(product.getFreeShipping());
        assertEquals(new BigDecimal("5.99"), product.getShippingCost());
        assertEquals(0.3, product.getWeight());
        assertEquals(10.0, product.getWidth());
        assertEquals(15.0, product.getHeight());
        assertEquals(5.0, product.getLength());
        assertEquals(Arrays.asList("https://example.com/test.jpg"), product.getImages());
        assertEquals(Arrays.asList("Color: Red"), product.getAttributes());
        assertEquals(100, product.getViews());
        assertEquals(5, product.getSales());
        assertEquals(4.0, product.getRating());
        assertEquals(10, product.getRatingCount());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNotNull(product.getLastSoldAt());
        assertEquals(ProductCondition.NEW, product.getCondition());
        assertEquals("Test Brand", product.getBrand());
        assertEquals("Test Model", product.getModel());
        assertEquals(10, product.getStock());
        assertTrue(product.getAvailable());
        assertEquals("TEST-SKU", product.getSku());
        assertEquals("9876543210987", product.getBarcode());
        assertEquals(Arrays.asList("test", "product"), product.getTags());
        assertEquals("6 months warranty", product.getWarranty());
        assertEquals("14 days return", product.getReturnPolicy());
        assertTrue(product.getIsAvailable());
        assertTrue(product.getHasStock());
        assertFalse(product.getIsFreeShipping());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        // Given
        ProductResponseDto product1 = ProductResponseDto.builder()
                .id("test-id")
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

        ProductResponseDto product2 = ProductResponseDto.builder()
                .id("test-id")
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

    @Test
    void toString_ShouldContainImportantFields() {
        // When
        String toString = validProductResponse.toString();

        // Then
        assertTrue(toString.contains("iPhone 15 Pro Max"));
        assertTrue(toString.contains("f47ac10b-58cc-4372-a567-0e02b2c3d479"));
        assertTrue(toString.contains("1299.99"));
        assertTrue(toString.contains("USD"));
        assertTrue(toString.contains("Apple"));
    }

    @Test
    void calculatedFields_ShouldReflectCorrectValues() {
        // Given
        ProductResponseDto productWithStock = ProductResponseDto.builder()
                .id("test-id")
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
                .stock(5)
                .isAvailable(true)
                .hasStock(true)
                .isFreeShipping(true)
                .build();

        // When & Then
        assertTrue(productWithStock.getIsAvailable());
        assertTrue(productWithStock.getHasStock());
        assertTrue(productWithStock.getIsFreeShipping());
    }

    @Test
    void calculatedFields_WhenNoStock_ShouldReflectCorrectValues() {
        // Given
        ProductResponseDto productWithoutStock = ProductResponseDto.builder()
                .id("test-id")
                .title("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .currency("USD")
                .availableQuantity(0)
                .status(ProductStatus.ACTIVE)
                .category("Test Category")
                .subcategory("Test Subcategory")
                .sellerId("test-seller-id")
                .sellerName("Test Seller")
                .freeShipping(false)
                .condition(ProductCondition.NEW)
                .available(false)
                .stock(0)
                .isAvailable(false)
                .hasStock(false)
                .isFreeShipping(false)
                .build();

        // When & Then
        assertFalse(productWithoutStock.getIsAvailable());
        assertFalse(productWithoutStock.getHasStock());
        assertFalse(productWithoutStock.getIsFreeShipping());
    }

    @Test
    void images_ShouldHandleEmptyList() {
        // Given
        ProductResponseDto productWithEmptyImages = ProductResponseDto.builder()
                .id("test-id")
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
                .images(Arrays.asList())
                .build();

        // When & Then
        assertNotNull(productWithEmptyImages.getImages());
        assertTrue(productWithEmptyImages.getImages().isEmpty());
    }

    @Test
    void attributes_ShouldHandleEmptyList() {
        // Given
        ProductResponseDto productWithEmptyAttributes = ProductResponseDto.builder()
                .id("test-id")
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
                .attributes(Arrays.asList())
                .build();

        // When & Then
        assertNotNull(productWithEmptyAttributes.getAttributes());
        assertTrue(productWithEmptyAttributes.getAttributes().isEmpty());
    }

    @Test
    void tags_ShouldHandleEmptyList() {
        // Given
        ProductResponseDto productWithEmptyTags = ProductResponseDto.builder()
                .id("test-id")
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
                .tags(Arrays.asList())
                .build();

        // When & Then
        assertNotNull(productWithEmptyTags.getTags());
        assertTrue(productWithEmptyTags.getTags().isEmpty());
    }

    @Test
    void rating_ShouldHandleNullValues() {
        // Given
        ProductResponseDto productWithNullRating = ProductResponseDto.builder()
                .id("test-id")
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
                .rating(null)
                .ratingCount(null)
                .build();

        // When & Then
        assertNull(productWithNullRating.getRating());
        assertNull(productWithNullRating.getRatingCount());
    }

    @Test
    void dates_ShouldHandleNullValues() {
        // Given
        ProductResponseDto productWithNullDates = ProductResponseDto.builder()
                .id("test-id")
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
                .createdAt(null)
                .updatedAt(null)
                .lastSoldAt(null)
                .build();

        // When & Then
        assertNull(productWithNullDates.getCreatedAt());
        assertNull(productWithNullDates.getUpdatedAt());
        assertNull(productWithNullDates.getLastSoldAt());
    }
}
