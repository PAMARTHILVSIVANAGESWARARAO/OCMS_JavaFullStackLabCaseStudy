package com.example.OCMS.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OCMS.source.model.UserModel;

@Repository 
public interface UserRepository extends JpaRepository<UserModel , Integer>{
    // To verify admin existence 
    boolean existsByNameAndPassword(String name, String password);
}
