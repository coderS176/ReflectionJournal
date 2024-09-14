package com.example.ReflectionJournal.repository;

import com.example.ReflectionJournal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
}
