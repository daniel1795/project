package com.example.project.application.mapper;

import com.example.project.domain.model.Product;
import com.example.project.infraestructure.dto.ProductDto;
import com.example.project.infraestructure.dto.ProductRequestDto;
import com.example.project.infraestructure.dto.ProductResponseDto;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .availableQuantity(product.getAvailableQuantity())
                .status(product.getStatus())
                .category(product.getCategory())
                .subcategory(product.getSubcategory())
                .sellerId(product.getSellerId())
                .sellerName(product.getSellerName())
                .listingType(product.getListingType())
                .freeShipping(product.getFreeShipping())
                .shippingCost(product.getShippingCost())
                .weight(product.getWeight())
                .width(product.getWidth())
                .height(product.getHeight())
                .length(product.getLength())
                .images(product.getImages())
                .attributes(product.getAttributes())
                .views(product.getViews())
                .sales(product.getSales())
                .rating(product.getRating())
                .ratingCount(product.getRatingCount())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .lastSoldAt(product.getLastSoldAt())
                .condition(product.getCondition())
                .brand(product.getBrand())
                .model(product.getModel())
                .stock(product.getStock() != null ? product.getStock().getQuantity() : 0)
                .available(product.getAvailable())
                .sku(product.getSku())
                .barcode(product.getBarcode())
                .tags(product.getTags())
                .warranty(product.getWarranty())
                .returnPolicy(product.getReturnPolicy())
                .build();
    }
    
    public ProductResponseDto toResponseDto(Product product) {
        if (product == null) {
            return null;
        }
        
        return ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .availableQuantity(product.getAvailableQuantity())
                .status(product.getStatus())
                .category(product.getCategory())
                .subcategory(product.getSubcategory())
                .sellerId(product.getSellerId())
                .sellerName(product.getSellerName())
                .listingType(product.getListingType())
                .freeShipping(product.getFreeShipping())
                .shippingCost(product.getShippingCost())
                .weight(product.getWeight())
                .width(product.getWidth())
                .height(product.getHeight())
                .length(product.getLength())
                .images(product.getImages())
                .attributes(product.getAttributes())
                .views(product.getViews())
                .sales(product.getSales())
                .rating(product.getRating())
                .ratingCount(product.getRatingCount())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .lastSoldAt(product.getLastSoldAt())
                .condition(product.getCondition())
                .brand(product.getBrand())
                .model(product.getModel())
                .stock(product.getStock() != null ? product.getStock().getQuantity() : 0)
                .available(product.getAvailable())
                .sku(product.getSku())
                .barcode(product.getBarcode())
                .tags(product.getTags())
                .warranty(product.getWarranty())
                .returnPolicy(product.getReturnPolicy())
                .isAvailable(product.isAvailable())
                .hasStock(product.hasStock())
                .isFreeShipping(product.isFreeShipping())
                .build();
    }
    
    public Product toDomain(ProductRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        return Product.newBuilder(null) // ID se asignará por la base de datos
                .basicInfo(
                    requestDto.getTitle(),
                    requestDto.getDescription(),
                    requestDto.getPrice(),
                    requestDto.getCurrency()
                )
                .categorization(
                    requestDto.getCategory(),
                    requestDto.getSubcategory()
                )
                .seller(
                    requestDto.getSellerId(),
                    requestDto.getSellerName()
                )
                .listing(
                    requestDto.getListingType(),
                    requestDto.getFreeShipping()
                )
                .condition(requestDto.getCondition())
                .stock(requestDto.getAvailableQuantity())
                .status(requestDto.getStatus())
                .shipping(requestDto.getShippingCost())
                .dimensions(
                    requestDto.getWeight(),
                    requestDto.getWidth(),
                    requestDto.getHeight(),
                    requestDto.getLength()
                )
                .media(
                    requestDto.getImages(),
                    requestDto.getAttributes()
                )
                .identification(
                    requestDto.getSku(),
                    requestDto.getBarcode(),
                    requestDto.getBrand(),
                    requestDto.getModel()
                )
                .policies(
                    requestDto.getWarranty(),
                    requestDto.getReturnPolicy(),
                    requestDto.getTags()
                )
                .build();
    }
    
    public Product toDomain(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        
        return Product.newBuilder(dto.getId())
                .basicInfo(
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getPrice(),
                    dto.getCurrency()
                )
                .categorization(
                    dto.getCategory(),
                    dto.getSubcategory()
                )
                .seller(
                    dto.getSellerId(),
                    dto.getSellerName()
                )
                .listing(
                    dto.getListingType(),
                    dto.getFreeShipping()
                )
                .condition(dto.getCondition())
                .stock(dto.getAvailableQuantity())
                .status(dto.getStatus())
                .shipping(dto.getShippingCost())
                .dimensions(
                    dto.getWeight(),
                    dto.getWidth(),
                    dto.getHeight(),
                    dto.getLength()
                )
                .media(
                    dto.getImages(),
                    dto.getAttributes()
                )
                .identification(
                    dto.getSku(),
                    dto.getBarcode(),
                    dto.getBrand(),
                    dto.getModel()
                )
                .policies(
                    dto.getWarranty(),
                    dto.getReturnPolicy(),
                    dto.getTags()
                )
                .build();
    }
}