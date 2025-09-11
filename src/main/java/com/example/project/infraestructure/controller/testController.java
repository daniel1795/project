package com.example.project.infraestructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.application.dto.testDto;
import com.example.project.application.port.in.testuseCase;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class testController {

    private final testuseCase tetuseCase;

    public testController(testuseCase tetuseCase){
        this.tetuseCase = tetuseCase;
    }

    @GetMapping("/{idCard}")
    public ResponseEntity<testDto> getById(@PathVariable String idCard) {
        Optional<testDto> user = tetuseCase.getReq(idCard);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<testDto> createUser(@Valid @RequestBody testDto userDto) {
        testDto created = tetuseCase.createUser(userDto);
        return ResponseEntity.ok(created);
    }
}
