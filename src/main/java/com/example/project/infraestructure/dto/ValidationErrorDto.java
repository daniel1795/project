package com.example.project.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDto {
    
    private String field;
    private String message;
    private String code;
    private Object rejectedValue;
    
    // Constructor simplificado para casos comunes
    public ValidationErrorDto(String field, String message, String code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }
}
