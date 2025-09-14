package com.example.project.application.exception;

public class ProductNotFoundException extends RuntimeException {
    
    public ProductNotFoundException(String message) {
        super(message);
    }
    
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructor para casos comunes
    public static ProductNotFoundException withId(String productId) {
        return new ProductNotFoundException("Producto no encontrado con ID: " + productId);
    }
}
