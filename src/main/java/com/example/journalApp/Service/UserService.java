package com.example.journalApp.Service;

import com.example.journalApp.Repository.UserRepo;
import com.example.journalApp.Entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public  void saveEntry(UserEntry userEntry){
        userRepo.save(userEntry);
    }
    public List<UserEntry> getAll(){
        return userRepo.findAll();
    }
public Optional<UserEntry> findById(ObjectId id){
        return userRepo.findById(id);
}
public  void deleteById(ObjectId id){
    userRepo.deleteById(id);
}
public UserEntry findByUserName(String userName){
        return userRepo.findByUserName(userName);
}
}
