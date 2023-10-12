package com.example.Mania.controllers;
import com.example.Mania.dtos.UserDto;
import com.example.Mania.services.UserService;
import com.example.Mania.utils.MessageSendlerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping ("/unsecured")
    public String UnsecuredData() {
        return "Unsecured data";
    }
    @GetMapping ("/secured")
    public String securedData () {
        return "Secured data";
    }
    @GetMapping("/admin")
    public String unsecuredData() {
        return "admin data";
    }
    @GetMapping("/info")
    public ResponseEntity<?> info(Principal principal){
        return ResponseEntity.ok(principal.getName());
    }
//    @GetMapping("/code")
//    public ResponseEntity<String> getCode() {
//        String code = messageSendlerUtils.generateCode();
//        UserDto userDto = new UserDto(1L , "Check" , "n_turugeldiev@kbtu.kz" );
//        messageSendlerUtils.processMessage(code , userDto);
//        return ResponseEntity.ok(code);
//    }
}
