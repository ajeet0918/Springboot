package com.example.journalApp.Controller;
import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Entity.UserEntry;
import com.example.journalApp.Service.JournalEntrySevice;
import com.example.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {


    @Autowired
private JournalEntrySevice journalEntrySevice;
    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        UserEntry userByDb1 = userService.findByUserName(userName);

        if (userByDb1 == null) {
            // User not found, return 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> all = userByDb1.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            // Return the list of journal entries with 200 OK
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        // No journal entries found, return 204 No Content
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


@PostMapping("/{userName}")
public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry , @PathVariable String userName){

        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntrySevice.saveEntry(myEntry, userName);
            return  new ResponseEntity<>(myEntry , HttpStatus.CREATED);
        } catch (Exception e){
            return  new ResponseEntity<>(myEntry , HttpStatus.BAD_REQUEST);
        }
}
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) {
        try {
            ObjectId objectId = new ObjectId(myId);
            Optional<JournalEntry> journalEntry = journalEntrySevice.findById(objectId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

@DeleteMapping("/id/{userName}/{myId}")
public ResponseEntity<?> deleteJournalentryById(@PathVariable ObjectId myId , @PathVariable String userName){
   journalEntrySevice.deleteById(myId , userName);
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@PutMapping("/id/{userName}/{id}")
public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id ,
                                                @RequestBody JournalEntry newEntry ,
                                                @PathVariable String userName) {
  JournalEntry old =  journalEntrySevice.findById(id).orElse(null) ;
if (old != null){
      old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent(): old.getContent());
      old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
      journalEntrySevice.saveEntry(old);
      return new ResponseEntity<>(old , HttpStatus.OK);
  }
    return new ResponseEntity<>(old, HttpStatus.NOT_FOUND);
}
}
