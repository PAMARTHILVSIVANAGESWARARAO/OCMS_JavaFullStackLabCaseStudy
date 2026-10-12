package com.example.OCMS.source.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.OCMS.source.model.UserModel;
import com.example.OCMS.source.service.UserService;

@RestController 
@RequestMapping("api/users/auth")
public class UserController {

    @Autowired 
    UserService service;
    
    @PostMapping ("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel model){
       return service.registerUser(model);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel model){
        String email = model.getEmail();
        String password = model.getPassword();

        return service.loginUser(email , password);
    }
     @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(
            @RequestHeader(
                value = "Authorization",
                required = false
            ) String authorization) {

        if (authorization == null
                || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "JWT token required"));
        }

        return service.dashboard(authorization);
    
    
    }
}
