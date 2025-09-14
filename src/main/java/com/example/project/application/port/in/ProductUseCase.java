package com.example.project.application.port.in;

import java.util.List;
import java.util.Optional;

import com.example.project.application.exception.ProductNotFoundException;
import com.example.project.infraestructure.dto.ProductRequestDto;
import com.example.project.infraestructure.dto.ProductResponseDto;

public interface ProductUseCase {
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto); 
    public ProductResponseDto updateProduct(String id, ProductRequestDto productRequestDto) throws ProductNotFoundException; 
    public Optional<ProductResponseDto> filterById(String id);
    public Optional<ProductResponseDto> filterByTitle(String title);
    public Optional<List<ProductResponseDto>> filterByKeyword(String keyword);
    public List<ProductResponseDto> listAll();
}
