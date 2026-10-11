package com.example.OCMS;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping 
public class Health {
   
    @GetMapping 
    public HashMap<String , String> getHealth(){
        HashMap<String , String> response = new HashMap<>();
        response.put("health" , "Application Working");
        return response;

    }
}
