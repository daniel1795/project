package com.example.project.domain.common.exceptions;

public class InsufficientStockException extends DomainException {
    
    public InsufficientStockException(String message) {
        super(message);
    }
    
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
