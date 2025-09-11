package com.example.project.infraestructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {

    @Id
    @Column(name = "id_card", length = 20, nullable = false)
    private String idCard;
    
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 50)
    private String city;
    
    @Column(length = 50)
    private String department;
    
    @Column(length = 50)
    private String country;
    
    @Column(length = 100)
    private String address;
}
