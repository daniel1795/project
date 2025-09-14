package com.example.project.application.port.out;

import java.util.List;
import java.util.Optional;

import com.example.project.domain.model.Product;

public interface ProductRepository {
    public Product save(Product product);
    public Optional<Product> findById(String id);
    public Optional<Product> findByTitle(String title);
    public List<Product> findAll();
    public List<Product> findByKeyword(String keyword);
    public int countByKeyword(String keyword);
}
