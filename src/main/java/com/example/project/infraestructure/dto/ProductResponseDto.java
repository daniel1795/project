package com.example.project.infraestructure.dto;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para productos
 * 
 * Incluye todos los campos del producto más campos calculados:
 * - isAvailable: Indica si el producto está disponible (campo calculado)
 * - hasStock: Indica si hay stock disponible (campo calculado)
 * - isFreeShipping: Indica si el envío es gratuito (campo calculado)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta para productos")
public class ProductResponseDto {
    
    @Schema(description = "ID único del producto (UUID)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private String id;
    
    @Schema(description = "Título del producto", example = "iPhone 15 Pro Max")
    private String title;
    
    @Schema(description = "Descripción detallada del producto", example = "Smartphone Apple con las últimas características")
    private String description;
    
    @Schema(description = "Precio del producto", example = "1299.99")
    private BigDecimal price;
    
    @Schema(description = "Moneda del precio", example = "USD")
    private String currency;
    
    @Schema(description = "Cantidad disponible en stock", example = "50")
    private Integer availableQuantity;
    
    @Schema(description = "Estado del producto", example = "ACTIVE")
    private ProductStatus status;
    
    @Schema(description = "Categoría principal del producto", example = "Electrónicos")
    private String category;
    
    @Schema(description = "Subcategoría del producto", example = "Smartphones")
    private String subcategory;
    
    @Schema(description = "ID único del vendedor (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    private String sellerId;
    
    @Schema(description = "Nombre del vendedor", example = "TechStore Pro")
    private String sellerName;
    
    @Schema(description = "Tipo de listado del producto", example = "GOLD_SPECIAL")
    private ListingType listingType;
    
    @Schema(description = "Indica si el envío es gratuito", example = "true")
    private Boolean freeShipping;
    
    @Schema(description = "Costo del envío", example = "15.99")
    private BigDecimal shippingCost;
    
    @Schema(description = "Peso del producto en kilogramos", example = "0.5")
    private Double weight; // en kg
    
    @Schema(description = "Ancho del producto en centímetros", example = "15.0")
    private Double width; // en cm
    
    @Schema(description = "Alto del producto en centímetros", example = "20.0")
    private Double height; // en cm
    
    @Schema(description = "Largo del producto en centímetros", example = "8.0")
    private Double length; // en cm
    
    @Schema(description = "Lista de URLs de imágenes del producto", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private List<String> images;
    
    @Schema(description = "Atributos específicos del producto", example = "[\"Color: Azul\", \"Memoria: 256GB\", \"Procesador: A17 Pro\"]")
    private List<String> attributes; // Atributos específicos del producto
    
    @Schema(description = "Número de visualizaciones del producto", example = "1250")
    private Integer views;
    
    @Schema(description = "Número de ventas del producto", example = "45")
    private Integer sales;
    
    @Schema(description = "Calificación promedio del producto (0.0 - 5.0)", example = "4.5")
    private Double rating;
    
    @Schema(description = "Número de calificaciones recibidas", example = "128")
    private Integer ratingCount;
    
    @Schema(description = "Fecha y hora de creación del producto", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha y hora de última actualización", example = "2024-01-20T14:45:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Fecha y hora de última venta", example = "2024-01-18T16:20:00")
    private LocalDateTime lastSoldAt;
    
    @Schema(description = "Condición del producto", example = "NEW")
    private ProductCondition condition;
    
    @Schema(description = "Marca del producto", example = "Apple")
    private String brand;
    
    @Schema(description = "Modelo del producto", example = "iPhone 15 Pro Max")
    private String model;
    
    @Schema(description = "Cantidad en stock", example = "25")
    private Integer stock;
    
    @Schema(description = "Indica si el producto está disponible para venta", example = "true")
    private Boolean available;
    
    @Schema(description = "Código único del producto (SKU)", example = "IPH15PM-256-NT")
    private String sku; // Código único del producto
    
    @Schema(description = "Código de barras del producto", example = "1234567890123")
    private String barcode; // Código de barras
    
    @Schema(description = "Etiquetas para búsqueda y categorización", example = "[\"smartphone\", \"apple\", \"premium\", \"5g\"]")
    private List<String> tags; // Etiquetas para búsqueda
    
    @Schema(description = "Información de garantía del producto", example = "1 año de garantía del fabricante")
    private String warranty; // Información de garantía
    
    @Schema(description = "Política de devolución del producto", example = "30 días para devolución")
    private String returnPolicy; // Política de devolución
    
    // Campos calculados para la respuesta
    @Schema(description = "Indica si el producto está disponible (campo calculado)", example = "true")
    private Boolean isAvailable;
    
    @Schema(description = "Indica si hay stock disponible (campo calculado)", example = "true")
    private Boolean hasStock;
    
    @Schema(description = "Indica si el envío es gratuito (campo calculado)", example = "true")
    private Boolean isFreeShipping;
}
