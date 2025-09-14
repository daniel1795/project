package com.example.project.infraestructure.controller;

import com.example.project.application.exception.BusinessException;
import com.example.project.application.exception.ProductNotFoundException;
import com.example.project.infraestructure.dto.ErrorResponseDto;
import com.example.project.infraestructure.dto.ValidationErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Maneja errores de validación de DTOs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        
        String traceId = generateTraceId();
        String path = getCurrentPath(request);
        
        List<ValidationErrorDto> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapToValidationError)
                .collect(Collectors.toList());
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "VALIDATION_ERROR",
                "Los datos enviados no son válidos",
                validationErrors,
                path
        );
        errorResponse.setTraceId(traceId);
        
        logger.warn("Validation error [{}] on path {}: {}", traceId, path, validationErrors);
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Maneja errores cuando no se encuentra un producto
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(
            ProductNotFoundException ex, 
            WebRequest request) {
        
        String traceId = generateTraceId();
        String path = getCurrentPath(request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "PRODUCT_NOT_FOUND",
                ex.getMessage(),
                path
        );
        errorResponse.setTraceId(traceId);
        
        logger.warn("Product not found [{}] on path {}: {}", traceId, path, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    /**
     * Maneja errores de negocio
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(
            BusinessException ex, 
            WebRequest request) {
        
        String traceId = generateTraceId();
        String path = getCurrentPath(request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getErrorCode(),
                ex.getMessage(),
                path
        );
        errorResponse.setTraceId(traceId);
        
        logger.warn("Business error [{}] on path {}: {}", traceId, path, ex.getMessage());
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Maneja errores generales de runtime
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(
            RuntimeException ex, 
            WebRequest request) {
        
        String traceId = generateTraceId();
        String path = getCurrentPath(request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "INTERNAL_ERROR",
                "Ha ocurrido un error interno. Por favor, inténtalo de nuevo.",
                path
        );
        errorResponse.setTraceId(traceId);
        
        logger.error("Runtime error [{}] on path {}: {}", traceId, path, ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * Maneja errores no controlados
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception ex, 
            WebRequest request) {
        
        String traceId = generateTraceId();
        String path = getCurrentPath(request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "UNKNOWN_ERROR",
                "Ha ocurrido un error inesperado. Por favor, contacta al soporte técnico.",
                path
        );
        errorResponse.setTraceId(traceId);
        
        logger.error("Unknown error [{}] on path {}: {}", traceId, path, ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * Convierte FieldError a ValidationError
     */
    private ValidationErrorDto mapToValidationError(FieldError fieldError) {
        return ValidationErrorDto.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .code("VALIDATION_ERROR")
                .rejectedValue(fieldError.getRejectedValue())
                .build();
    }
    
    /**
     * Genera un ID único para tracking de errores
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    
    /**
     * Obtiene la ruta actual de la request
     */
    private String getCurrentPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
