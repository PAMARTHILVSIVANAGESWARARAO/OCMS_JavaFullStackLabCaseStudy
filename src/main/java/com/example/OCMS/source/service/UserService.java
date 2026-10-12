package com.example.OCMS.source.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.OCMS.source.model.UserModel;
import com.example.OCMS.source.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public ResponseEntity<?> registerUser(UserModel model) {
        try {
            if (repository.findByEmail(model.getEmail()).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Error: Email is already registered!");
            }

            // FIX: Reject only if the role is NEITHER student NOR instructor
            if (!model.getRole().equalsIgnoreCase("student") && !model.getRole().equalsIgnoreCase("instructor")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Error: Role Must Be Student Or Instructor Only");
            }

            model.setPassword(encoder.encode(model.getPassword()));
            UserModel savedUser = repository.save(model);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration: " + e.getMessage());
        }
    }

    public ResponseEntity<?> loginUser(String email, String password) {
        try {
            Optional<UserModel> userOpt = repository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Error: Invalid email or password!");
            }

            UserModel user = userOpt.get();

            if (!encoder.matches(password, user.getPassword())) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Error: Invalid email or password!");
            }

            // Authentication successful -> Generate the JWT token!
            String token = jwtService.generateToken(user.getEmail());

            // Return the token inside a clean response map or object
            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during login: " + e.getMessage());
        }
    }

    public ResponseEntity<?> dashboard(String authorization) {

        if (authorization == null
                || !authorization.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("JWT token is required");
        }

        try {
            String token = authorization.substring(7);

            // Extract email after validating the JWT
            String email = jwtService.extractEmail(token);

            // Retrieve user from database
            UserModel user = repository.findByEmail(email)
                    .orElse(null);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }

            // Return dashboard data
            Map<String, Object> dashboard = new HashMap<>();
            dashboard.put("message", "Dashboard loaded successfully");
            dashboard.put("id", user.getId());
            dashboard.put("email", user.getEmail());
            dashboard.put("role", user.getRole());

            return ResponseEntity.ok(dashboard);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired JWT token");
        }
    }
}
