package com.example.project.infraestructure.persistance.adapter;

import java.util.Optional;

import com.example.project.application.port.out.testRepository;
import com.example.project.domain.model.test;
import com.example.project.infraestructure.persistance.entity.TestEntity;
import com.example.project.infraestructure.persistance.repository.TestJpaRepository;

public class TestRepositoyAdapter implements testRepository {

    private final TestJpaRepository testJpaRepository;

    public TestRepositoyAdapter(TestJpaRepository testJpaRepository){
        this.testJpaRepository = testJpaRepository;
    }

    @Override
    public test save(test user) {
        TestEntity entity = mapToEntity(user);
        TestEntity saved = testJpaRepository.save(entity);
        return mapToDomain(saved);
    }
    
    @Override
    public Optional<test> findById(String idCard) {
        return testJpaRepository.findById(idCard)
        .map(this::mapToDomain);
    }

    private TestEntity mapToEntity(test user) {
        TestEntity entity = new TestEntity();
        entity.setIdCard(user.getIdCard());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
        entity.setCity(user.getCity());
        entity.setDepartment(user.getDepartment());
        entity.setCountry(user.getCountry());
        entity.setAddress(user.getAddress());
        return entity;
    }

    private test mapToDomain(TestEntity entity) {
        return new test(
            entity.getIdCard(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getCity(),
            entity.getDepartment(),
            entity.getCountry(),
            entity.getAddress()
        );
    }
}
