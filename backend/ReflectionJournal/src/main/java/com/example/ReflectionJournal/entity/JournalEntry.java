package com.example.ReflectionJournal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JOURNAL_ENTRY")
public class JournalEntry {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String title;
    private String content;
    public LocalDateTime dateTime;
}
