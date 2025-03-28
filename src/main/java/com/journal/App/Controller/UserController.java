package com.journal.App.Controller;
import com.journal.App.Entity.JournalEntity;
import com.journal.App.Entity.User;
import com.journal.App.Service.JournalEntryService;
import com.journal.App.Service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserEntryService ues;

    @GetMapping
    public List<User> getall(){
      return ues.getallentrys();
    }

    @PostMapping
    public List<User> save(@RequestBody User user)
    {
        return ues.save(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<List<User>> update(@RequestBody User user,
                                             @PathVariable String userName)
    {
        User userindb=ues.findByUserName(userName);
        if(userindb!=null)
        {
            userindb.setPassword(user.getPassword());
            userindb.setUserName(user.getUserName());
            ues.save(userindb);
        }
        List<User> r=ues.getallentrys();
        return new ResponseEntity<>(r,HttpStatus.OK);
    }



}