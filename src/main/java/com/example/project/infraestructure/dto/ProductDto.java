package com.example.project.infraestructure.dto;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private String id;
    
    @NotBlank(message = "El título del producto es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String title;
    
    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String description;
    
    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal price;
    
    @NotNull(message = "La moneda es obligatoria")
    @Pattern(regexp = "^(ARS|USD|BRL|MXN|COP|CLP|UYU|PEN)$", 
             message = "La moneda debe ser una de: ARS, USD, BRL, MXN, COP, CLP, UYU, PEN")
    private String currency;
    
    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private Integer availableQuantity;
    
    @NotNull(message = "El estado del producto es obligatorio")
    private ProductStatus status;
    
    @NotBlank(message = "La categoría del producto es obligatoria")
    private String category;
    
    @NotBlank(message = "La subcategoría del producto es obligatoria")
    private String subcategory;
    
    @NotNull(message = "El vendedor es obligatorio")
    private String sellerId;
    
    @NotBlank(message = "El nombre del vendedor es obligatorio")
    private String sellerName;
    
    private ListingType listingType;
    
    @NotNull(message = "El envío es obligatorio")
    private Boolean freeShipping;
    
    private BigDecimal shippingCost;
    
    @Min(value = 0, message = "El peso no puede ser negativo")
    private Double weight; // en kg
    
    @Min(value = 0, message = "El ancho no puede ser negativo")
    private Double width; // en cm
    
    @Min(value = 0, message = "La altura no puede ser negativa")
    private Double height; // en cm
    
    @Min(value = 0, message = "La longitud no puede ser negativa")
    private Double length; // en cm
    
    private List<String> images;
    
    private List<String> attributes; // Atributos específicos del producto
    
    @Min(value = 0, message = "Las visitas no pueden ser negativas")
    private Integer views;
    
    @Min(value = 0, message = "Las ventas no pueden ser negativas")
    private Integer sales;
    
    @DecimalMin(value = "0.0", message = "La calificación no puede ser negativa")
    @DecimalMax(value = "5.0", message = "La calificación no puede ser mayor a 5")
    private Double rating;
    
    @Min(value = 0, message = "El número de calificaciones no puede ser negativo")
    private Integer ratingCount;
    
    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private LocalDateTime lastSoldAt;
    
    @NotNull(message = "La condición del producto es obligatoria")
    private ProductCondition condition;
    
    private String brand;
    
    private String model;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    
    @NotNull(message = "La disponibilidad es obligatoria")
    private Boolean available;
    
    private String sku; // Código único del producto
    
    private String barcode; // Código de barras
    
    private List<String> tags; // Etiquetas para búsqueda
    
    private String warranty; // Información de garantía
    
    private String returnPolicy; // Política de devolución
}
