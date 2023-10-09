package com.example.Mania.services;

import com.example.Mania.dtos.RegistrationUserDto;
import com.example.Mania.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    ResponseEntity<?> getAllUsers();
    Optional<User> findByUsername(String username);
    User createUser(RegistrationUserDto registrationUserDto);


}
