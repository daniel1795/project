package com.example.project.infraestructure.dto;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para crear o actualizar un producto
 * 
 * Campos requeridos:
 * - title: Título del producto (máximo 100 caracteres)
 * - description: Descripción detallada (máximo 2000 caracteres)
 * - price: Precio del producto (debe ser mayor a 0)
 * - currency: Moneda (ARS, USD, BRL, MXN, COP, CLP, UYU, PEN)
 * - availableQuantity: Cantidad disponible en stock (mínimo 0)
 * - status: Estado del producto (ACTIVE, PAUSED, CLOSED, INACTIVE)
 * - category: Categoría principal del producto
 * - subcategory: Subcategoría del producto
 * - sellerId: ID único del vendedor (UUID)
 * - sellerName: Nombre del vendedor
 * - freeShipping: Indica si el envío es gratuito
 * - condition: Condición del producto (NEW, USED, NOT_SPECIFIED)
 * - available: Indica si el producto está disponible para venta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear o actualizar un producto")
public class ProductRequestDto {
    
    @NotBlank(message = "El título del producto es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    @Schema(description = "Título del producto", example = "iPhone 15 Pro Max", maxLength = 100)
    private String title;
    
    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    @Schema(description = "Descripción detallada del producto", example = "Smartphone Apple con las últimas características", maxLength = 2000)
    private String description;
    
    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    @Schema(description = "Precio del producto", example = "1299.99", minimum = "0.01")
    private BigDecimal price;
    
    @NotNull(message = "La moneda es obligatoria")
    @Pattern(regexp = "^(ARS|USD|BRL|MXN|COP|CLP|UYU|PEN)$", 
             message = "La moneda debe ser una de: ARS, USD, BRL, MXN, COP, CLP, UYU, PEN")
    @Schema(description = "Moneda del precio", example = "USD", allowableValues = {"ARS", "USD", "BRL", "MXN", "COP", "CLP", "UYU", "PEN"})
    private String currency;
    
    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    @Schema(description = "Cantidad disponible en stock", example = "50", minimum = "0")
    private Integer availableQuantity;
    
    @NotNull(message = "El estado del producto es obligatorio")
    @Schema(description = "Estado del producto", example = "ACTIVE")
    private ProductStatus status;
    
    @NotBlank(message = "La categoría del producto es obligatoria")
    @Schema(description = "Categoría principal del producto", example = "Electrónicos", maxLength = 100)
    private String category;
    
    @NotBlank(message = "La subcategoría del producto es obligatoria")
    @Schema(description = "Subcategoría del producto", example = "Smartphones", maxLength = 100)
    private String subcategory;
    
    @NotNull(message = "El vendedor es obligatorio")
    @Schema(description = "ID único del vendedor (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    private String sellerId;
    
    @NotBlank(message = "El nombre del vendedor es obligatorio")
    @Schema(description = "Nombre del vendedor", example = "TechStore Pro", maxLength = 100)
    private String sellerName;
    
    @Schema(description = "Tipo de listado del producto", example = "GOLD_SPECIAL")
    private ListingType listingType;
    
    @NotNull(message = "El envío es obligatorio")
    @Schema(description = "Indica si el envío es gratuito", example = "true")
    private Boolean freeShipping;
    
    @Schema(description = "Costo del envío", example = "15.99", minimum = "0")
    private BigDecimal shippingCost;
    
    @Min(value = 0, message = "El peso no puede ser negativo")
    @Schema(description = "Peso del producto en kilogramos", example = "0.5", minimum = "0")
    private Double weight;
    
    @Min(value = 0, message = "El ancho no puede ser negativo")
    @Schema(description = "Ancho del producto en centímetros", example = "15.0", minimum = "0")
    private Double width;
    
    @Min(value = 0, message = "La altura no puede ser negativa")
    @Schema(description = "Alto del producto en centímetros", example = "20.0", minimum = "0")
    private Double height;
    
    @Min(value = 0, message = "La longitud no puede ser negativa")
    @Schema(description = "Largo del producto en centímetros", example = "8.0", minimum = "0")
    private Double length;
    
    @Schema(description = "Lista de URLs de imágenes del producto", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private List<String> images;
    
    @Schema(description = "Atributos específicos del producto", example = "[\"Color: Azul\", \"Memoria: 256GB\", \"Procesador: A17 Pro\"]")
    private List<String> attributes;
    
    @NotNull(message = "La condición del producto es obligatoria")
    @Schema(description = "Condición del producto", example = "NEW")
    private ProductCondition condition;
    
    @Schema(description = "Marca del producto", example = "Apple", maxLength = 50)
    private String brand;
    
    @Schema(description = "Modelo del producto", example = "iPhone 15 Pro Max", maxLength = 100)
    private String model;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad en stock", example = "25", minimum = "0")
    private Integer stock;
    
    @NotNull(message = "La disponibilidad es obligatoria")
    @Schema(description = "Indica si el producto está disponible para venta", example = "true")
    private Boolean available;
    
    @Schema(description = "Código único del producto (SKU)", example = "IPH15PM-256-NT", maxLength = 50)
    private String sku;
    
    @Schema(description = "Código de barras del producto", example = "1234567890123", maxLength = 20)
    private String barcode;
    
    @Schema(description = "Etiquetas para búsqueda y categorización", example = "[\"smartphone\", \"apple\", \"premium\", \"5g\"]")
    private List<String> tags;
    
    @Schema(description = "Información de garantía del producto", example = "1 año de garantía del fabricante", maxLength = 500)
    private String warranty;
    
    @Schema(description = "Política de devolución del producto", example = "30 días para devolución", maxLength = 500)
    private String returnPolicy;
}
