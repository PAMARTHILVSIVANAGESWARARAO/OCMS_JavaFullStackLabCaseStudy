package com.example.OCMS.source.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.OCMS.source.model.CourseModel;
import com.example.OCMS.source.service.CourseService;

@RestController
@RequestMapping("/api/dashboard/courses")
public class CourseController {

    @Autowired 
    CourseService service;
    
    @PostMapping("/add-course")
    public ResponseEntity<?> addCourse(@RequestHeader("Authorization") String authorization , @RequestBody CourseModel model){
        return service.addCourse(authorization , model);
        

    }

}
