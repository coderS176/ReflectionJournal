package com.example.ReflectionJournal.controller;

import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.service.UserDetailsServiceImpl;
import com.example.ReflectionJournal.service.UserService;
import com.example.ReflectionJournal.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/health-check")
    String checkHealth(){
        return "Okay";
    }
    @PostMapping("/signup")
    public User signup(@RequestBody User user){
        System.out.println("create-user called");
        return userService.saveNewUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Incorrect UserName or Password",HttpStatus.BAD_REQUEST);
        }
    }
}
