package com.example.project.infraestructure.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.infraestructure.persistance.entity.TestEntity;

@Repository
public interface TestJpaRepository extends JpaRepository<TestEntity, String>  {

}
