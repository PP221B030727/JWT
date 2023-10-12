package com.example.Mania.services;

import com.example.Mania.config.RegistrationConfig;
import com.example.Mania.dtos.JwtRequest;
import com.example.Mania.dtos.JwtResponse;
import com.example.Mania.dtos.RegistrationUserDto;
import com.example.Mania.dtos.UserDto;
import com.example.Mania.exceptions.AppError;
import com.example.Mania.models.User;
import com.example.Mania.utils.JwtTokenUtils;
import com.example.Mania.utils.MessageSendlerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager autenticationManager;
    private final MessageSendlerUtils messageSendlerUtils;
    private final RegistrationConfig registrationConfig;

    @Override
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        try{
            autenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value() , "login or password is wrong") , HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        System.out.println(userDetails);
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @Override
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto){
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value (), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if(userService.findByUsername(registrationUserDto.getUsername()).isPresent()){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value (), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId() , user.getUsername() , user.getEmail()));
    }

    @Override
    public ResponseEntity<String> sendCode(RegistrationUserDto registrationUserDto) {
        String code = messageSendlerUtils.generateCode();
        registrationUserDto.setGeneratedCode(code);
        RegistrationUserDto regUser = registrationConfig.registrationUserDto();
        regUser.setUsername(registrationUserDto.getUsername());
        regUser.setEmail(registrationUserDto.getEmail());
        regUser.setPassword(registrationUserDto.getPassword());
        regUser.setConfirmPassword(registrationUserDto.getConfirmPassword());
        regUser.setGeneratedCode(code);
        regUser.setIssuedDate(new Date());
        messageSendlerUtils.processMessage(code , registrationUserDto);
        return ResponseEntity.ok("Code was sended your email");
    }

    @Override
    public ResponseEntity<?> checkCode(String codeFromUser) {
        RegistrationUserDto registrationUserDto = registrationConfig.registrationUserDto();
        System.out.println(registrationUserDto);
        if(registrationUserDto.getGeneratedCode().equals(codeFromUser)){
            Date currentTime = new Date();
            long timeDifference = currentTime.getTime() - registrationUserDto.getIssuedDate().getTime();
            long minutesDifference = timeDifference / (60 * 1000);
            boolean isTrue = Math.abs(minutesDifference) < 1;
            if(isTrue){
                return createNewUser(registrationUserDto);
            }
        }
        return ResponseEntity.badRequest().body("Wrong code or time of code left");
    }
}
