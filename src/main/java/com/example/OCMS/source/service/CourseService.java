package com.example.OCMS.source.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.OCMS.source.model.CourseModel;
import com.example.OCMS.source.model.UserModel;
import com.example.OCMS.source.repository.CourseRepository;
import com.example.OCMS.source.repository.UserRepository;

@Service
public class CourseService {
    @Autowired
    CourseRepository repository;

    @Autowired 
    UserRepository userRepository;

    @Autowired 
    JwtService jwtService;




    public ResponseEntity<?> addCourse(String authorization , CourseModel model) {

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
            UserModel user = userRepository.findByEmail(email)
                    .orElse(null);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }

            if(user.getRole().equals("admin")){

                repository.save(model);
                return ResponseEntity.status(HttpStatus.CREATED).body(model);


            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin is The Onlyone to add courses");
                
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired JWT token");
        }

    }

}
