package com.example.project.application.port.out;

import java.util.Optional;

import com.example.project.domain.model.test;

public interface testRepository {
    Optional<test> findById(String idCard);
    test save(test user);
}
