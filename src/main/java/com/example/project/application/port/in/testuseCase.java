package com.example.project.application.port.in;

import java.util.Optional;

import com.example.project.application.dto.testDto;

public interface testuseCase {
    Optional<testDto> getReq(String idCard);
    testDto createUser(testDto userDto);
}
