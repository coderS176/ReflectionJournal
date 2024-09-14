package com.example.ReflectionJournal.service;

import com.example.ReflectionJournal.entity.JournalEntry;
import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.repository.JournalRepository;
import com.example.ReflectionJournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryService {
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserService userService;


    // save journal by user
    public JournalEntry saveJournal(JournalEntry journalEntry,String userName){
        try {
            User user = userService.findByUserName(userName);
            JournalEntry saved =  journalRepository.save(journalEntry);
            user.getJournalEntries().add(saved);

            // we do this then we will get the error (using an instance of the UserService class instead of
            // calling it directly in a static context)
            //User savedUser  = UserService.saveUser(user);
            User savedUser = userService.saveUser(user);
            return saved;
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    //for updating journal
    public JournalEntry saveJournal(JournalEntry journalEntry){
       return journalRepository.save(journalEntry);
    }

    //user ki journal list
    public List<JournalEntry> getJournalsByUser(String userName){
        User user = userService.findByUserName(userName);
        return user.getJournalEntries();
    }

}
