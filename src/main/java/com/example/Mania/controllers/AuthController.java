package com.example.Mania.controllers;

import com.example.Mania.dtos.JwtRequest;
import com.example.Mania.dtos.RegistrationUserDto;

import com.example.Mania.services.AuthService;

import com.example.Mania.utils.MessageSendlerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){

        return authService.sendCode(registrationUserDto);
    }
    @PostMapping("/registration/code")
    public ResponseEntity<?> confirmCode(@RequestParam String code){
        System.out.println(code);
        return ResponseEntity.ok(authService.checkCode(code));
    }
}
