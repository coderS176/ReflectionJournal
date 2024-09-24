package com.example.ReflectionJournal.service;

import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public User saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public boolean deleteUser(int id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public User updateUser(int id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Check and update fields if they are not null or empty
            if (!userDetails.getUserName().isEmpty()) {
                user.setUserName(userDetails.getUserName());
            }

            if (!userDetails.getEmail().isEmpty()) {
                user.setEmail(userDetails.getEmail());
            }

            if (!userDetails.getPassword().isEmpty()) {
                user.setPassword(userDetails.getPassword());
            }

            // Save the updated user to the database
            return userRepository.save(user);
        } else {
            // User not found
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }


    // get all Users List
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // delete by id
    public Boolean deleteUser(User user) {
        int id = user.getId();

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            // If the user exists, delete by id
            userRepository.deleteById(id);
            return true;
        } else {
            // User does not exist
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    // get user by Id
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
