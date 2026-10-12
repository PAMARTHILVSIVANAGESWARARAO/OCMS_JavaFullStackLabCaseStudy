package com.example.OCMS.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OCMS.source.model.CourseModel;

@Repository 
public interface CourseRepository extends JpaRepository<CourseModel , Integer> {
    
}
