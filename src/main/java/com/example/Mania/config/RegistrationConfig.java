package com.example.Mania.config;

import com.example.Mania.dtos.RegistrationUserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationConfig {
    @Bean
    public RegistrationUserDto registrationUserDto(){
        return new RegistrationUserDto();
    }
}
