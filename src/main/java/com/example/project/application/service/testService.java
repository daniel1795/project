package com.example.project.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project.application.dto.testDto;
import com.example.project.application.port.in.testuseCase;
import com.example.project.application.port.out.testRepository;
import com.example.project.domain.model.test;

@Service
public class testService implements testuseCase {

    private final testRepository testRepo;
    public testService(testRepository testRepo){
        this.testRepo = testRepo;
    }

    @Override
    public Optional<testDto> getReq( String idCard) {
        return testRepo.findById(idCard)
        .map(this::mapToDto);
    }

    @Override
    public testDto createUser(testDto userDto) {
        test user = mapToDomain(userDto);
        test saved = testRepo.save(user);
        return mapToDto(saved);
    }

    private test mapToDomain(testDto dto) {
        return new test(
            dto.getIdCard(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhone(),
            dto.getCity(),
            dto.getDepartment(),
            dto.getCountry(),
            dto.getAddress()
        );
    }
    private testDto mapToDto(test user) {
        return new testDto(
            user.getIdCard(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhone(),
            user.getCity(),
            user.getDepartment(),
            user.getCountry(),
            user.getAddress()
        );
    }

}
