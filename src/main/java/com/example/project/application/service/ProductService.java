package com.example.project.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.project.application.exception.ProductNotFoundException;
import com.example.project.application.mapper.ProductMapper;
import com.example.project.application.port.in.ProductUseCase;
import com.example.project.application.port.out.ProductRepository;
import com.example.project.domain.model.Product;
import com.example.project.infraestructure.dto.ProductRequestDto;
import com.example.project.infraestructure.dto.ProductResponseDto;

@Service
public class ProductService implements ProductUseCase {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.toDomain(productRequestDto);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDto(saved);
    }

    @Override
    public Optional<ProductResponseDto> filterById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productMapper::toResponseDto);
    }

    @Override
    public Optional<ProductResponseDto> filterByTitle(String title) {
        Optional<Product> product = productRepository.findByTitle(title);
        return product.map(productMapper::toResponseDto);
    }

    @Override
    public List<ProductResponseDto> listAll() {
        List<Product> product = productRepository.findAll();
        return product.stream()
                      .map(productMapper::toResponseDto)
                      .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(String id, ProductRequestDto productRequestDto) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw ProductNotFoundException.withId(id);
        }
        
        Product productToUpdate = productMapper.toDomain(productRequestDto);
        
        Product updatedProduct = Product.newBuilder(id)
                .basicInfo(productToUpdate.getTitle(), productToUpdate.getDescription(), 
                          productToUpdate.getPrice(), productToUpdate.getCurrency())
                .categorization(productToUpdate.getCategory(), productToUpdate.getSubcategory())
                .seller(productToUpdate.getSellerId(), productToUpdate.getSellerName())
                .listing(productToUpdate.getListingType(), productToUpdate.getFreeShipping())
                .condition(productToUpdate.getCondition())
                .stock(productToUpdate.getStock().getQuantity())
                .status(productToUpdate.getStatus())
                .shipping(productToUpdate.getShippingCost())
                .dimensions(productToUpdate.getWeight(), productToUpdate.getWidth(), 
                           productToUpdate.getHeight(), productToUpdate.getLength())
                .media(productToUpdate.getImages(), productToUpdate.getAttributes())
                .identification(productToUpdate.getSku(), productToUpdate.getBarcode(), 
                               productToUpdate.getBrand(), productToUpdate.getModel())
                .policies(productToUpdate.getWarranty(), productToUpdate.getReturnPolicy(), 
                         productToUpdate.getTags())
                .views(productToUpdate.getViews())
                .sales(productToUpdate.getSales())
                .rating(productToUpdate.getRatingObject())
                .lastSoldAt(productToUpdate.getLastSoldAt())
                .build();
        
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    public Optional<List<ProductResponseDto>> filterByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Optional.empty();
        }
        
        List<Product> products = productRepository.findByKeyword(keyword.trim().toLowerCase());
        
        if (products.isEmpty()) {
            return Optional.empty();
        }
        
        List<ProductResponseDto> responseDtos = products.stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
        
        return Optional.of(responseDtos);
    }

    
}
