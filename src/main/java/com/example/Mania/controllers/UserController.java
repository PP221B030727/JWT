package com.example.Mania.controllers;
import com.example.Mania.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
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
}
