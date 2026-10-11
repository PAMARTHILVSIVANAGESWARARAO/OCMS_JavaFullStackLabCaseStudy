package com.example.OCMS.source.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.OCMS.source.model.UserModel;
import com.example.OCMS.source.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin already exists using the boolean query
        if (!userRepository.existsByNameAndPassword("admin", "admin")) {
            UserModel admin = new UserModel();
            admin.setName("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@ocms.com");
            admin.setContact("1234567890");
            admin.setRole("admin");

            userRepository.save(admin);
            System.out.println("+-------------------------------------------------------+");
            System.out.println("|         Default Admin inserted successfully!          |");
            System.out.println("+-------------------------------------------------------+");

        } else {
            System.out.println("+-------------------------------------------------------+");
            System.out.println("|          Admin Alredy There  In  Database!            |");
            System.out.println("+-------------------------------------------------------+");

        }
    }
}
