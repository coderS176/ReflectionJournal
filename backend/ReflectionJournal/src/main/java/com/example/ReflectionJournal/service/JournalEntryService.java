package com.example.ReflectionJournal.service;

import com.example.ReflectionJournal.entity.JournalEntry;
import com.example.ReflectionJournal.entity.User;
import com.example.ReflectionJournal.repository.JournalRepository;
import com.example.ReflectionJournal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserService userService;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


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
            log.error("e: ", e);
            throw new RuntimeException(e);
        }
    }

    // updating journal
    public JournalEntry saveJournal(JournalEntry journalEntry){
       return journalRepository.save(journalEntry);
    }

    // user ki journal list
    public List<JournalEntry> getJournalsByUser(String userName){
        User user = userService.findByUserName(userName);
        return user.getJournalEntries();
    }

    public void saveEntry(JournalEntry jEntry) {
        jEntry.setTitle(bCryptPasswordEncoder.encode(jEntry.getTitle()));
        jEntry.setContent(bCryptPasswordEncoder.encode(jEntry.getContent()));
        journalRepository.save(jEntry);
    }

    // delete by Id
    public void deleteById(int id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId()==id);
        userService.saveUser(user);
        journalRepository.deleteById(id);
    }

    public Optional<JournalEntry> getById(int id) {
        bCryptPasswordEncoder.hashCode()
        return journalRepository.findById(id);
    }
}
