package com.journal.App.Service;
import com.journal.App.Entity.User;
import com.journal.App.Repository.userRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {
    @Autowired
    private userRepository ur;
    public List<User> save(User j)
    {
        ur.save(j);
        return ur.findAll();
    }
    public List<User> getallentrys()
    {
        return ur.findAll();
    }

    public User getone(ObjectId id)
    {
        Optional<User> h= ur.findById(id);
        if(h.isPresent())
        {
            return h.get();
        }
        return null;
    }

    public boolean deleteJournal(ObjectId id)
    {
        Optional<User> h= ur.findById(id);
        if(h.isPresent()) {
            ur.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByUserName(String username)
    {
        return ur.findByUserName(username);
    }
}
