package com.example.ReflectionJournal.controller;

import com.example.ReflectionJournal.entity.JournalEntry;
import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.service.JournalEntryService;
import com.example.ReflectionJournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/journal")
public class JournalController {
  @Autowired
  private JournalEntryService journalEntryService;
  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      User user = userService.findByUserName(userName);
      List<JournalEntry> all_entry = user.getJournalEntries();
      if(all_entry.isEmpty()){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(all_entry,HttpStatus.OK);
  }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry j_entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        j_entry.setDateTime(LocalDateTime.now());
        j_entry.setUser(user);
        journalEntryService.saveEntry(j_entry);
        return new ResponseEntity<>(j_entry,HttpStatus.CREATED);
    }

    @GetMapping("/id:{id}")
    public ResponseEntity<?> getEntryById(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId()==(id)).toList();
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id:{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId()==(id)).toList();
        if (!collect.isEmpty()) {
            journalEntryService.deleteById(id, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id:{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable int id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId()==id).toList();
        if (!collect.isEmpty()) {
            JournalEntry oldEntry = journalEntryService.getById(id).orElse(null);
            if (oldEntry != null) {
                oldEntry.setContent(!newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
                oldEntry.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
