package com.example.journalApp.Controller;

import com.example.journalApp.Entity.UserEntry;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")



public class UserController {
  @Autowired
    private UserService userService;

  @GetMapping
  public List<UserEntry>getAllusers(){
      return userService.getAll();
  }
  @PostMapping
  public void createUser(@RequestBody UserEntry userEntry){
      userService.saveEntry(userEntry);
  }

  @PutMapping("/{userName}")
  public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry , @PathVariable String userName) {
    UserEntry  userInDb = userService.findByUserName(userName);
    if (userInDb != null){
      userInDb.setUserName(userEntry.getUserName());
      userInDb.setPassword(userEntry.getPassword());
      userService.saveEntry(userInDb);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
