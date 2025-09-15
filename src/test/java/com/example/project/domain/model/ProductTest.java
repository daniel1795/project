package com.example.project.domain.model;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import com.example.project.domain.common.valueobjects.Rating;
import com.example.project.domain.common.valueobjects.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product.Builder productBuilder;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.now();
        productBuilder = Product.newBuilder("test-product-id");
    }

    @Test
    void newBuilder_ShouldCreateBuilderWithId() {
        // When
        Product.Builder builder = Product.newBuilder("test-id");

        // Then
        assertNotNull(builder);
    }

    @Test
    void build_WithMinimalData_ShouldCreateValidProduct() {
        // Given
        Product product = productBuilder
                .basicInfo("Test Product", "Test Description", new BigDecimal("99.99"), "USD")
                .categorization("Test Category", "Test Subcategory")
                .seller("test-seller-id", "Test Seller")
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .build();

        // Then
        assertNotNull(product);
        assertEquals("test-product-id", product.getId());
        assertEquals("Test Product", product.getTitle());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals("Test Category", product.getCategory());
        assertEquals("Test Subcategory", product.getSubcategory());
        assertEquals("test-seller-id", product.getSellerId());
        assertEquals("Test Seller", product.getSellerName());
        assertEquals(ProductCondition.NEW, product.getCondition());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        assertNotNull(product.getStock());
        assertEquals(10, product.getStock().getQuantity());
    }

    @Test
    void build_WithCompleteData_ShouldCreateValidProduct() {
        // Given
        Product product = productBuilder
                .basicInfo("iPhone 15 Pro Max", "Smartphone Apple con las últimas características", 
                          new BigDecimal("1299.99"), "USD")
                .categorization("Electrónicos", "Smartphones")
                .seller("550e8400-e29b-41d4-a716-446655440000", "TechStore Pro")
                .listing(ListingType.MERCADO_LIBRE, true)
                .condition(ProductCondition.NEW)
                .stock(50)
                .status(ProductStatus.ACTIVE)
                .shipping(new BigDecimal("0.00"))
                .dimensions(0.5, 15.0, 20.0, 8.0)
                .media(Arrays.asList("https://example.com/image1.jpg"), 
                      Arrays.asList("Color: Azul", "Memoria: 256GB"))
                .identification("IPH15PM-256-NT", "1234567890123", "Apple", "iPhone 15 Pro Max")
                .policies("1 año de garantía del fabricante", "30 días para devolución", 
                         Arrays.asList("smartphone", "apple", "premium"))
                .views(1250)
                .sales(45)
                .rating(Rating.of(4.5, 128))
                .lastSoldAt(testDateTime)
                .build();

        // Then
        assertNotNull(product);
        assertEquals("test-product-id", product.getId());
        assertEquals("iPhone 15 Pro Max", product.getTitle());
        assertEquals("Smartphone Apple con las últimas características", product.getDescription());
        assertEquals(new BigDecimal("1299.99"), product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals("Electrónicos", product.getCategory());
        assertEquals("Smartphones", product.getSubcategory());
        assertEquals("550e8400-e29b-41d4-a716-446655440000", product.getSellerId());
        assertEquals("TechStore Pro", product.getSellerName());
        assertEquals(ListingType.MERCADO_LIBRE, product.getListingType());
        assertTrue(product.getFreeShipping());
        assertEquals(new BigDecimal("0.00"), product.getShippingCost());
        assertEquals(0.5, product.getWeight());
        assertEquals(15.0, product.getWidth());
        assertEquals(20.0, product.getHeight());
        assertEquals(8.0, product.getLength());
        assertEquals(Arrays.asList("https://example.com/image1.jpg"), product.getImages());
        assertEquals(Arrays.asList("Color: Azul", "Memoria: 256GB"), product.getAttributes());
        assertEquals(1250, product.getViews());
        assertEquals(45, product.getSales());
        assertNotNull(product.getRatingObject());
        assertEquals(4.5, product.getRating());
        assertEquals(128, product.getRatingCount());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNotNull(product.getLastSoldAt());
        assertEquals(ProductCondition.NEW, product.getCondition());
        assertEquals("Apple", product.getBrand());
        assertEquals("iPhone 15 Pro Max", product.getModel());
        assertEquals("IPH15PM-256-NT", product.getSku());
        assertEquals("1234567890123", product.getBarcode());
        assertEquals(Arrays.asList("smartphone", "apple", "premium"), product.getTags());
        assertEquals("1 año de garantía del fabricante", product.getWarranty());
        assertEquals("30 días para devolución", product.getReturnPolicy());
    }

    @Test
    void build_WithOptionalFields_ShouldCreateValidProduct() {
        // Given
        Product product = productBuilder
                .basicInfo("Test Product", "Test Description", new BigDecimal("99.99"), "USD")
                .categorization("Test Category", "Test Subcategory")
                .seller("test-seller-id", "Test Seller")
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .listing(ListingType.MERCADO_PAGO, false)
                .shipping(new BigDecimal("5.99"))
                .dimensions(0.3, 10.0, 15.0, 5.0)
                .media(Arrays.asList("https://example.com/test.jpg"), 
                      Arrays.asList("Color: Red"))
                .identification("TEST-SKU", "9876543210987", "Test Brand", "Test Model")
                .policies("6 months warranty", "14 days return", 
                         Arrays.asList("test", "product"))
                .views(100)
                .sales(5)
                .rating(Rating.of(4.0, 10))
                .lastSoldAt(null)
                .build();

        // Then
        assertNotNull(product);
        assertEquals(ListingType.MERCADO_PAGO, product.getListingType());
        assertFalse(product.getFreeShipping());
        assertEquals(new BigDecimal("5.99"), product.getShippingCost());
        assertEquals(0.3, product.getWeight());
        assertEquals(10.0, product.getWidth());
        assertEquals(15.0, product.getHeight());
        assertEquals(5.0, product.getLength());
        assertEquals(Arrays.asList("https://example.com/test.jpg"), product.getImages());
        assertEquals(Arrays.asList("Color: Red"), product.getAttributes());
        assertEquals(100, product.getViews());
        assertEquals(5, product.getSales());
        assertNotNull(product.getRatingObject());
        assertEquals(4.0, product.getRating());
        assertEquals(10, product.getRatingCount());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNull(product.getLastSoldAt());
        assertEquals("Test Brand", product.getBrand());
        assertEquals("Test Model", product.getModel());
        assertEquals("TEST-SKU", product.getSku());
        assertEquals("9876543210987", product.getBarcode());
        assertEquals(Arrays.asList("test", "product"), product.getTags());
        assertEquals("6 months warranty", product.getWarranty());
        assertEquals("14 days return", product.getReturnPolicy());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Given
        Product product1 = productBuilder
                .basicInfo("Product 1", "Description 1", new BigDecimal("100.00"), "USD")
                .categorization("Category 1", "Subcategory 1")
                .seller("seller-1", "Seller 1")
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .build();

        Product product2 = Product.newBuilder("test-product-id")
                .basicInfo("Product 2", "Description 2", new BigDecimal("200.00"), "EUR")
                .categorization("Category 2", "Subcategory 2")
                .seller("seller-2", "Seller 2")
                .condition(ProductCondition.USED)
                .stock(20)
                .status(ProductStatus.INACTIVE)
                .build();

        // When & Then
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void equals_WithDifferentId_ShouldReturnFalse() {
        // Given
        Product product1 = productBuilder
                .basicInfo("Product 1", "Description 1", new BigDecimal("100.00"), "USD")
                .categorization("Category 1", "Subcategory 1")
                .seller("seller-1", "Seller 1")
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .build();

        Product product2 = Product.newBuilder("different-id")
                .basicInfo("Product 1", "Description 1", new BigDecimal("100.00"), "USD")
                .categorization("Category 1", "Subcategory 1")
                .seller("seller-1", "Seller 1")
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .build();

        // When & Then
        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void toString_ShouldContainImportantFields() {
        // Given
        Product product = productBuilder
                .basicInfo("Test Product", "Test Description", new BigDecimal("99.99"), "USD")
                .categorization("Test Category", "Test Subcategory")
                .seller("test-seller-id", "Test Seller")
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .build();

        // When
        String toString = product.toString();

        // Then
        assertTrue(toString.contains("test-product-id"));
        assertTrue(toString.contains("Test Product"));
        assertTrue(toString.contains("99.99"));
        assertTrue(toString.contains("USD"));
    }

    @Test
    void builder_ShouldSupportFluentInterface() {
        // When
        Product product = productBuilder
                .basicInfo("Test Product", "Test Description", new BigDecimal("99.99"), "USD")
                .categorization("Test Category", "Test Subcategory")
                .seller("test-seller-id", "Test Seller")
                .listing(ListingType.MERCADO_LIBRE, true)
                .condition(ProductCondition.NEW)
                .stock(10)
                .status(ProductStatus.ACTIVE)
                .shipping(new BigDecimal("0.00"))
                .dimensions(0.5, 15.0, 20.0, 8.0)
                .media(Arrays.asList("https://example.com/image1.jpg"), 
                      Arrays.asList("Color: Azul"))
                .identification("TEST-SKU", "1234567890123", "Test Brand", "Test Model")
                .policies("1 year warranty", "30 days return", 
                         Arrays.asList("test", "product"))
                .views(100)
                .sales(5)
                .rating(Rating.of(4.5, 10))
                .lastSoldAt(testDateTime)
                .build();

        // Then
        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals("Test Category", product.getCategory());
        assertEquals("Test Subcategory", product.getSubcategory());
        assertEquals("test-seller-id", product.getSellerId());
        assertEquals("Test Seller", product.getSellerName());
        assertEquals(ListingType.MERCADO_LIBRE, product.getListingType());
        assertTrue(product.getFreeShipping());
        assertEquals(new BigDecimal("0.00"), product.getShippingCost());
        assertEquals(0.5, product.getWeight());
        assertEquals(15.0, product.getWidth());
        assertEquals(20.0, product.getHeight());
        assertEquals(8.0, product.getLength());
        assertEquals(Arrays.asList("https://example.com/image1.jpg"), product.getImages());
        assertEquals(Arrays.asList("Color: Azul"), product.getAttributes());
        assertEquals(100, product.getViews());
        assertEquals(5, product.getSales());
        assertNotNull(product.getRatingObject());
        assertEquals(4.5, product.getRating());
        assertEquals(10, product.getRatingCount());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNotNull(product.getLastSoldAt());
        assertEquals(ProductCondition.NEW, product.getCondition());
        assertEquals("Test Brand", product.getBrand());
        assertEquals("Test Model", product.getModel());
        assertEquals("TEST-SKU", product.getSku());
        assertEquals("1234567890123", product.getBarcode());
        assertEquals(Arrays.asList("test", "product"), product.getTags());
        assertEquals("1 year warranty", product.getWarranty());
        assertEquals("30 days return", product.getReturnPolicy());
    }
}
