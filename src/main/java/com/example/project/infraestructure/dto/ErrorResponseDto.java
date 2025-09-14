package com.example.project.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    
    private String error;
    private String message;
    private List<ValidationErrorDto> details;
    private LocalDateTime timestamp;
    private String path;
    private String traceId; // Para tracking de errores en logs
    
    // Constructor para errores simples sin detalles
    public ErrorResponseDto(String error, String message, String path) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
    
    // Constructor para errores con detalles de validación
    public ErrorResponseDto(String error, String message, List<ValidationErrorDto> details, String path) {
        this.error = error;
        this.message = message;
        this.details = details;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
