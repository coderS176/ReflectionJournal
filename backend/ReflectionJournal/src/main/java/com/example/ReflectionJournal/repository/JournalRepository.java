package com.example.ReflectionJournal.repository;

import com.example.ReflectionJournal.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<JournalEntry, Integer> {
}
