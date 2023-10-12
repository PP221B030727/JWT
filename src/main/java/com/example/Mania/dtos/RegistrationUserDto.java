package com.example.Mania.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class RegistrationUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Date issuedDate;
    private String generatedCode;
}
