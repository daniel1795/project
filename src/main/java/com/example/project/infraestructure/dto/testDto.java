package com.example.project.infraestructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class testDto {

    @NotBlank(message = "ID Card is required")
    @Size(max = 20, message = "ID Card must not exceed 20 characters")
    private String idCard;
    
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
    
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;
    
    @Size(max = 50, message = "Department must not exceed 50 characters")
    private String department;
    
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;
    
    @Size(max = 100, message = "Address must not exceed 100 characters")
    private String address;
}
