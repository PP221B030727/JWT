package com.example.Mania.services;

import com.example.Mania.dtos.JwtRequest;
import com.example.Mania.dtos.RegistrationUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> createAuthToken(JwtRequest authRequest);
    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto);
}
