package com.example.ReflectionJournal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "journal_entries")
public class JournalEntry {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "Title can't be Empty")
    private String title;
    private String content;
    private LocalDateTime dateTime;

    @ManyToOne // Define Many-to-One relationship
    @JsonBackReference // This side will be ignored during serialization
    @JoinColumn(name = "user_id", nullable = false) // Foreign key reference to User
    private User user;
}
