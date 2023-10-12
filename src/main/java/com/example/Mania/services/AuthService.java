package com.example.Mania.services;

import com.example.Mania.dtos.JwtRequest;
import com.example.Mania.dtos.RegistrationUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> createAuthToken(JwtRequest authRequest);
    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto);
    ResponseEntity<String> sendCode(RegistrationUserDto registrationUserDto);
    ResponseEntity<?> checkCode(String codeFromUser);
}
