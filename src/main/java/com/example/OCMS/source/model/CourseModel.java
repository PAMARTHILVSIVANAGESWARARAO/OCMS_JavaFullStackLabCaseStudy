package com.example.OCMS.source.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data 
@Entity(name="courses")
public class CourseModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_id", unique = true, nullable = false)
    private Long courseId;

    @Column(name = "course_title", unique = true, nullable = false)
    private String courseTitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description = "Not given";


    
    
}
