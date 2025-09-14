package com.example.project.infraestructure.controller;

import com.example.project.application.port.in.ProductUseCase;
import com.example.project.application.exception.ProductNotFoundException;
import com.example.project.infraestructure.dto.ProductRequestDto;
import com.example.project.infraestructure.dto.ProductResponseDto;
import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private ProductRequestDto validProductRequest;
    private ProductResponseDto validProductResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Para soportar LocalDateTime

        // Configurar datos de prueba
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
    }

    @Test
    void findProductById_WhenProductExists_ShouldReturnProduct() throws Exception {
        // Given
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        when(productUseCase.filterById(productId)).thenReturn(Optional.of(validProductResponse));

        // When & Then
        mockMvc.perform(get("/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.title").value("iPhone 15 Pro Max"))
                .andExpect(jsonPath("$.price").value(1299.99))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.isAvailable").value(true));

        verify(productUseCase, times(1)).filterById(productId);
    }

    @Test
    void findProductById_WhenProductNotExists_ShouldReturn404() throws Exception {
        // Given
        String productId = "non-existent-id";
        when(productUseCase.filterById(productId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productUseCase, times(1)).filterById(productId);
    }

    @Test
    void findProductByTitle_WhenProductExists_ShouldReturnProduct() throws Exception {
        // Given
        String title = "iPhone 15 Pro Max";
        when(productUseCase.filterByTitle(title)).thenReturn(Optional.of(validProductResponse));

        // When & Then
        mockMvc.perform(get("/api/product/title/{title}", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.brand").value("Apple"));

        verify(productUseCase, times(1)).filterByTitle(title);
    }

    @Test
    void findProductByTitle_WhenProductNotExists_ShouldReturn404() throws Exception {
        // Given
        String title = "Non-existent Product";
        when(productUseCase.filterByTitle(title)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/product/title/{title}", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productUseCase, times(1)).filterByTitle(title);
    }

    @Test
    void findProductByKeyword_WhenProductsFound_ShouldReturnProductList() throws Exception {
        // Given
        String keyword = "iPhone";
        List<ProductResponseDto> products = Arrays.asList(validProductResponse);
        when(productUseCase.filterByKeyword(keyword)).thenReturn(Optional.of(products));

        // When & Then
        mockMvc.perform(get("/api/product/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("iPhone 15 Pro Max"));

        verify(productUseCase, times(1)).filterByKeyword(keyword);
    }

    @Test
    void findProductByKeyword_WhenNoProductsFound_ShouldReturn404() throws Exception {
        // Given
        String keyword = "non-existent";
        when(productUseCase.filterByKeyword(keyword)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/product/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productUseCase, times(1)).filterByKeyword(keyword);
    }

    @Test
    void listAll_ShouldReturnAllProducts() throws Exception {
        // Given
        List<ProductResponseDto> products = Arrays.asList(validProductResponse);
        when(productUseCase.listAll()).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("iPhone 15 Pro Max"));

        verify(productUseCase, times(1)).listAll();
    }

    @Test
    void createProduct_WithValidData_ShouldReturnCreatedProduct() throws Exception {
        // Given
        when(productUseCase.createProduct(any(ProductRequestDto.class))).thenReturn(validProductResponse);

        // When & Then
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validProductRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
                .andExpect(jsonPath("$.title").value("iPhone 15 Pro Max"));

        verify(productUseCase, times(1)).createProduct(any(ProductRequestDto.class));
    }

    @Test
    void createProduct_WithInvalidData_ShouldReturn400() throws Exception {
        // Given
        ProductRequestDto invalidRequest = ProductRequestDto.builder()
                .title("") // Título vacío - inválido
                .price(new BigDecimal("-100.00")) // Precio negativo - inválido
                .build();

        // When & Then
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(productUseCase, never()).createProduct(any(ProductRequestDto.class));
    }

    @Test
    void updateProduct_WithValidData_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        when(productUseCase.updateProduct(eq(productId), any(ProductRequestDto.class)))
                .thenReturn(validProductResponse);

        // When & Then
        mockMvc.perform(put("/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validProductRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.title").value("iPhone 15 Pro Max"));

        verify(productUseCase, times(1)).updateProduct(eq(productId), any(ProductRequestDto.class));
    }

    @Test
    void updateProduct_WithInvalidData_ShouldReturn400() throws Exception {
        // Given
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        ProductRequestDto invalidRequest = ProductRequestDto.builder()
                .title("") // Título vacío - inválido
                .build();

        // When & Then
        mockMvc.perform(put("/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(productUseCase, never()).updateProduct(anyString(), any(ProductRequestDto.class));
    }

    @Test
    void updateProduct_WhenProductNotFound_ShouldReturn404() throws Exception {
        // Given
        String productId = "non-existent-id";
        when(productUseCase.updateProduct(eq(productId), any(ProductRequestDto.class)))
                .thenThrow(new ProductNotFoundException("Product not found with id: " + productId));

        // When & Then
        mockMvc.perform(put("/api/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validProductRequest)))
                .andExpect(status().isNotFound());

        verify(productUseCase, times(1)).updateProduct(eq(productId), any(ProductRequestDto.class));
    }
}
