package com.example.journalApp.Service;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Entity.UserEntry;
import com.example.journalApp.Repository.journalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntrySevice {

    @Autowired
    private journalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

  @Transactional
    public  void saveEntry(JournalEntry JournalEntry, String userName){
        UserEntry user = userService.findByUserName(userName);
        JournalEntry saved  = journalEntryRepo.save(JournalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }
    public  void saveEntry(JournalEntry JournalEntry){
        journalEntryRepo.save(JournalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }
public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
}
public  void deleteById(ObjectId id , String userName){
      UserEntry user = userService.findByUserName(userName);
      user.getJournalEntries().removeIf(x-> x.getId().equals(id));
      userService.saveEntry(user);
      journalEntryRepo.deleteById(id);
}

}
