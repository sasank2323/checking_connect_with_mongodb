package com.journal.App.Repository;

import com.journal.App.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<User, ObjectId>{

    User findByUserName(String User);
}
