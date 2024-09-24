package com.example.ReflectionJournal.controller;

import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.service.JournalEntryService;
import com.example.ReflectionJournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/alluser")
    List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getUserById:{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
