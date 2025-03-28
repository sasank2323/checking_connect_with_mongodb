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
import org.springframework.web.service.annotation.PutExchange;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/journal")
public class JournalEntryController {
     @Autowired
     private JournalEntryService jes;
     @Autowired
     private UserEntryService ues;

    @GetMapping("/abc/{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName)
    {
        User user=ues.findByUserName(userName);
        List<JournalEntity> a=user.getJournalEntries();
        if(a !=null && !a.isEmpty()){
            return new ResponseEntity<>(a,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<List<JournalEntity>> posting(@RequestBody JournalEntity data,
                                                       @PathVariable String userName) {
        try{
            data.setDate(LocalDateTime.now());
             jes.saveJournalEntry(data,userName);
             return new ResponseEntity<>(jes.getallentrys(),HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<JournalEntity> one(@PathVariable ObjectId id) {
        if(jes.getone(id)!=null)
        {
            return new ResponseEntity<>(jes.getone(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}/{userName}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id,@PathVariable String userName) {
        if(jes.deleteJournal(id,userName))
        {
            return new ResponseEntity<>("deleted",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("not found by using id",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/{userName}")
    public List<JournalEntity> update(@PathVariable ObjectId id
            ,@RequestBody JournalEntity newentry,@RequestBody String userName)
    {
      JournalEntity jj=jes.getone(id);
      if(jj!=null)
      {
          jj.setContent(newentry.getContent()!=null && !newentry.getContent().equals("")
                  ? newentry.getContent():jj.getContent());
          jj.setTitle(newentry.getTitle()!=null && !newentry.getContent().equals("")?
                  newentry.getTitle() : jj.getTitle());
      }
      return jes.save(jj);
    }

}