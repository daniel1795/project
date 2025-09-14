package com.example.project.application.service;

import com.example.project.application.exception.ProductNotFoundException;
import com.example.project.application.mapper.ProductMapper;
import com.example.project.application.port.out.ProductRepository;
import com.example.project.domain.model.Product;
import com.example.project.infraestructure.dto.ProductRequestDto;
import com.example.project.infraestructure.dto.ProductResponseDto;
import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import com.example.project.domain.common.valueobjects.Stock;
import com.example.project.domain.common.valueobjects.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductRequestDto validProductRequest;
    private ProductResponseDto validProductResponse;
    private Product validProduct;

    @BeforeEach
    void setUp() {
        setupTestData();
    }

    private void setupTestData() {
        // ProductRequestDto válido
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
                .shippingCost(new BigDecimal("0.00"))
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

        // ProductResponseDto válido
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
                .images(Arrays.asList("https://example.com/image1.jpg"))
                .attributes(Arrays.asList("Color: Azul", "Memoria: 256GB"))
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
                .tags(Arrays.asList("smartphone", "apple", "premium"))
                .warranty("1 año de garantía del fabricante")
                .returnPolicy("30 días para devolución")
                .isAvailable(true)
                .hasStock(true)
                .isFreeShipping(true)
                .build();

        // Product de dominio válido (simulado)
        validProduct = mock(Product.class);
        when(validProduct.getId()).thenReturn("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        when(validProduct.getTitle()).thenReturn("iPhone 15 Pro Max");
        when(validProduct.getDescription()).thenReturn("Smartphone Apple con las últimas características");
        when(validProduct.getPrice()).thenReturn(new BigDecimal("1299.99"));
        when(validProduct.getCurrency()).thenReturn("USD");
        when(validProduct.getStatus()).thenReturn(ProductStatus.ACTIVE);
        when(validProduct.getCategory()).thenReturn("Electrónicos");
        when(validProduct.getSubcategory()).thenReturn("Smartphones");
        when(validProduct.getSellerId()).thenReturn("550e8400-e29b-41d4-a716-446655440000");
        when(validProduct.getSellerName()).thenReturn("TechStore Pro");
        when(validProduct.getListingType()).thenReturn(ListingType.MERCADO_LIBRE);
        when(validProduct.getFreeShipping()).thenReturn(true);
        when(validProduct.getShippingCost()).thenReturn(new BigDecimal("0.00"));
        when(validProduct.getWeight()).thenReturn(0.5);
        when(validProduct.getWidth()).thenReturn(15.0);
        when(validProduct.getHeight()).thenReturn(20.0);
        when(validProduct.getLength()).thenReturn(8.0);
        when(validProduct.getImages()).thenReturn(Arrays.asList("https://example.com/image1.jpg"));
        when(validProduct.getAttributes()).thenReturn(Arrays.asList("Color: Azul", "Memoria: 256GB"));
        when(validProduct.getCondition()).thenReturn(ProductCondition.NEW);
        when(validProduct.getBrand()).thenReturn("Apple");
        when(validProduct.getModel()).thenReturn("iPhone 15 Pro Max");
        when(validProduct.getSku()).thenReturn("IPH15PM-256-NT");
        when(validProduct.getBarcode()).thenReturn("1234567890123");
        when(validProduct.getTags()).thenReturn(Arrays.asList("smartphone", "apple", "premium"));
        when(validProduct.getWarranty()).thenReturn("1 año de garantía del fabricante");
        when(validProduct.getReturnPolicy()).thenReturn("30 días para devolución");
        when(validProduct.getStock()).thenReturn(Stock.of(25));
        when(validProduct.getRatingObject()).thenReturn(Rating.of(4.5, 128));
    }

    @Test
    void createProduct_WithValidData_ShouldReturnCreatedProduct() {
        // Given
        when(productMapper.toDomain(validProductRequest)).thenReturn(validProduct);
        when(productRepository.save(validProduct)).thenReturn(validProduct);
        when(productMapper.toResponseDto(validProduct)).thenReturn(validProductResponse);

        // When
        ProductResponseDto result = productService.createProduct(validProductRequest);

        // Then
        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getId());
        assertEquals("iPhone 15 Pro Max", result.getTitle());
        assertEquals(new BigDecimal("1299.99"), result.getPrice());

        verify(productMapper, times(1)).toDomain(validProductRequest);
        verify(productRepository, times(1)).save(validProduct);
        verify(productMapper, times(1)).toResponseDto(validProduct);
    }

    @Test
    void filterById_WhenProductExists_ShouldReturnProduct() {
        // Given
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        when(productRepository.findById(productId)).thenReturn(Optional.of(validProduct));
        when(productMapper.toResponseDto(validProduct)).thenReturn(validProductResponse);

        // When
        Optional<ProductResponseDto> result = productService.filterById(productId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.get().getId());
        assertEquals("iPhone 15 Pro Max", result.get().getTitle());

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toResponseDto(validProduct);
    }

    @Test
    void filterById_WhenProductNotExists_ShouldReturnEmpty() {
        // Given
        String productId = "non-existent-id";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        Optional<ProductResponseDto> result = productService.filterById(productId);

        // Then
        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void filterByTitle_WhenProductExists_ShouldReturnProduct() {
        // Given
        String title = "iPhone 15 Pro Max";
        when(productRepository.findByTitle(title)).thenReturn(Optional.of(validProduct));
        when(productMapper.toResponseDto(validProduct)).thenReturn(validProductResponse);

        // When
        Optional<ProductResponseDto> result = productService.filterByTitle(title);

        // Then
        assertTrue(result.isPresent());
        assertEquals("iPhone 15 Pro Max", result.get().getTitle());

        verify(productRepository, times(1)).findByTitle(title);
        verify(productMapper, times(1)).toResponseDto(validProduct);
    }

    @Test
    void filterByTitle_WhenProductNotExists_ShouldReturnEmpty() {
        // Given
        String title = "Non-existent Product";
        when(productRepository.findByTitle(title)).thenReturn(Optional.empty());

        // When
        Optional<ProductResponseDto> result = productService.filterByTitle(title);

        // Then
        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findByTitle(title);
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void listAll_ShouldReturnAllProducts() {
        // Given
        List<Product> products = Arrays.asList(validProduct);
        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toResponseDto(validProduct)).thenReturn(validProductResponse);

        // When
        List<ProductResponseDto> result = productService.listAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("iPhone 15 Pro Max", result.get(0).getTitle());

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toResponseDto(validProduct);
    }

    @Test
    void listAll_WhenNoProducts_ShouldReturnEmptyList() {
        // Given
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<ProductResponseDto> result = productService.listAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productRepository, times(1)).findAll();
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void filterByKeyword_WithValidKeyword_ShouldReturnProducts() {
        // Given
        String keyword = "iPhone";
        List<Product> products = Arrays.asList(validProduct);
        when(productRepository.findByKeyword(keyword.toLowerCase())).thenReturn(products);
        when(productMapper.toResponseDto(validProduct)).thenReturn(validProductResponse);

        // When
        Optional<List<ProductResponseDto>> result = productService.filterByKeyword(keyword);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("iPhone 15 Pro Max", result.get().get(0).getTitle());

        verify(productRepository, times(1)).findByKeyword(keyword.toLowerCase());
        verify(productMapper, times(1)).toResponseDto(validProduct);
    }

    @Test
    void filterByKeyword_WithEmptyKeyword_ShouldReturnEmpty() {
        // Given
        String keyword = "";

        // When
        Optional<List<ProductResponseDto>> result = productService.filterByKeyword(keyword);

        // Then
        assertFalse(result.isPresent());

        verify(productRepository, never()).findByKeyword(anyString());
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void filterByKeyword_WithNullKeyword_ShouldReturnEmpty() {
        // Given
        String keyword = null;

        // When
        Optional<List<ProductResponseDto>> result = productService.filterByKeyword(keyword);

        // Then
        assertFalse(result.isPresent());

        verify(productRepository, never()).findByKeyword(anyString());
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void filterByKeyword_WithWhitespaceKeyword_ShouldReturnEmpty() {
        // Given
        String keyword = "   ";

        // When
        Optional<List<ProductResponseDto>> result = productService.filterByKeyword(keyword);

        // Then
        assertFalse(result.isPresent());

        verify(productRepository, never()).findByKeyword(anyString());
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void filterByKeyword_WhenNoProductsFound_ShouldReturnEmpty() {
        // Given
        String keyword = "non-existent";
        when(productRepository.findByKeyword(keyword.toLowerCase())).thenReturn(Arrays.asList());

        // When
        Optional<List<ProductResponseDto>> result = productService.filterByKeyword(keyword);

        // Then
        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findByKeyword(keyword.toLowerCase());
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }

    @Test
    void updateProduct_WhenProductExists_ShouldReturnUpdatedProduct() {
        // Given
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        when(productRepository.findById(productId)).thenReturn(Optional.of(validProduct));
        when(productMapper.toDomain(validProductRequest)).thenReturn(validProduct);
        when(validProduct.getStock()).thenReturn(Stock.of(25));
        when(validProduct.getRatingObject()).thenReturn(Rating.of(4.5, 128));
        when(productRepository.save(any(Product.class))).thenReturn(validProduct);
        when(productMapper.toResponseDto(validProduct)).thenReturn(validProductResponse);

        // When
        ProductResponseDto result = productService.updateProduct(productId, validProductRequest);

        // Then
        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getId());
        assertEquals("iPhone 15 Pro Max", result.getTitle());

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toDomain(validProductRequest);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toResponseDto(validProduct);
    }

    @Test
    void updateProduct_WhenProductNotExists_ShouldThrowException() {
        // Given
        String productId = "non-existent-id";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(productId, validProductRequest);
        });

        assertEquals("Product not found with id: non-existent-id", exception.getMessage());

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, never()).toDomain(any(ProductRequestDto.class));
        verify(productRepository, never()).save(any(Product.class));
        verify(productMapper, never()).toResponseDto(any(Product.class));
    }
}
