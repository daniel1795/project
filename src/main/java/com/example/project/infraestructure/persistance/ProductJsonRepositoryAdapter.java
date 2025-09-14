package com.example.project.infraestructure.persistance;

import com.example.project.application.mapper.ProductMapper;
import com.example.project.application.port.out.ProductRepository;
import com.example.project.domain.model.Product;
import com.example.project.infraestructure.dto.ProductDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("json")
public class ProductJsonRepositoryAdapter implements ProductRepository {

    private final ObjectMapper objectMapper;
    private final String dataFilePath;
    private final ProductMapper productMapper;
    
    public ProductJsonRepositoryAdapter(ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.dataFilePath = "data/products.json";
        initializeDataFile();
    }

    private void initializeDataFile() {
        try {
            Path path = Paths.get(dataFilePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                objectMapper.writeValue(new File(dataFilePath), new ArrayList<ProductDto>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing data file", e);
        }
    }

    @Override
    public Product save(Product product) {
        try {
            List<ProductDto> products = loadAllProducts();
            
            // Si el producto tiene ID, es una actualización
            if (product.getId() != null) {
                products = products.stream()
                        .map(p -> p.getId().equals(product.getId()) ? productMapper.toDto(product) : p)
                        .collect(Collectors.toList());
            } else {
                // Si no tiene ID, es una creación - generar UUID
                ProductDto productDto = productMapper.toDto(product);
                productDto.setId(UUID.randomUUID().toString());
                products.add(productDto);
            }
            
            // Guardar en archivo
            saveAllProducts(products);
            
            // Retornar el producto con ID asignado
            return product.getId() != null ? product : 
                   productMapper.toDomain(products.get(products.size() - 1));
            
        } catch (Exception e) {
            throw new RuntimeException("Error saving product", e);
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .map(productMapper::toDomain);
        } catch (Exception e) {
            throw new RuntimeException("Error finding product by id: " + id, e);
        }
    }

    @Override
    public Optional<Product> findByTitle(String title) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getTitle() != null && p.getTitle().equals(title))
                    .findFirst()
                    .map(productMapper::toDomain);
        } catch (Exception e) {
            throw new RuntimeException("Error finding product by title: " + title, e);
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all products", e);
        }
    }

    private List<ProductDto> loadAllProducts() throws IOException {
        File file = new File(dataFilePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        
        return objectMapper.readValue(file, new TypeReference<List<ProductDto>>() {});
    }

    private void saveAllProducts(List<ProductDto> products) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(dataFilePath), products);
    }

    // Métodos adicionales útiles para el repositorio JSON
    
    public boolean deleteById(String id) {
        try {
            List<ProductDto> products = loadAllProducts();
            boolean removed = products.removeIf(p -> p.getId().equals(id));
            if (removed) {
                saveAllProducts(products);
            }
            return removed;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product by id: " + id, e);
        }
    }

    public List<Product> findByCategory(String category) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getCategory() != null && p.getCategory().equals(category))
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by category: " + category, e);
        }
    }

    public List<Product> findBySellerId(Long sellerId) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getSellerId() != null && p.getSellerId().equals(sellerId))
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by seller id: " + sellerId, e);
        }
    }

    public long count() {
        try {
            return loadAllProducts().size();
        } catch (Exception e) {
            throw new RuntimeException("Error counting products", e);
        }
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> matchesKeyword(p, keyword))
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by keyword: " + keyword, e);
        }
    }

    @Override
    public int countByKeyword(String keyword) {
        try {
            List<ProductDto> products = loadAllProducts();
            return (int) products.stream()
                    .filter(p -> matchesKeyword(p, keyword))
                    .count();
        } catch (Exception e) {
            throw new RuntimeException("Error counting products by keyword: " + keyword, e);
        }
    }

    private boolean matchesKeyword(ProductDto product, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return false;
        }
        
        String lowerKeyword = keyword.toLowerCase();
        
        // Buscar en campos principales que permiten identificar el producto fácilmente
        return (product.getTitle() != null && product.getTitle().toLowerCase().contains(lowerKeyword)) ||
               (product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerKeyword)) ||
               (product.getSubcategory() != null && product.getSubcategory().toLowerCase().contains(lowerKeyword)) ||
               (product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerKeyword)) ||
               (product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerKeyword)) ||
               (product.getTags() != null && product.getTags().stream()
                       .anyMatch(tag -> tag != null && tag.toLowerCase().contains(lowerKeyword))) ||
               (product.getModel() != null && product.getModel().toLowerCase().contains(lowerKeyword)) ||
               (product.getSku() != null && product.getSku().toLowerCase().contains(lowerKeyword));
    }
}