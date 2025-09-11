package com.example.project.infraestructure.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.infraestructure.persistance.entity.TestEntity;

public interface TestJpaRepository extends JpaRepository<TestEntity, String>  {

}
