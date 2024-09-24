package com.example.ReflectionJournal.controller;

import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    String checkHealth(){
        return "Okay";
    }
    @PostMapping("/create-user")
    public User createUser(@RequestBody User user){
        System.out.println("create-user called");
        return userService.saveNewUser(user);
    }
}
