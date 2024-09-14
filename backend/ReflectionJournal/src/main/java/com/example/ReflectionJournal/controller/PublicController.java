package com.example.ReflectionJournal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {
    @GetMapping("/health-check")
    String checkHealth(){
        return "Okay";
    }
    @PostMapping("create-user")
    public User
}
