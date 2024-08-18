package com.example.journalApp.Repository;

import com.example.journalApp.Entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUserName(String username);
}
