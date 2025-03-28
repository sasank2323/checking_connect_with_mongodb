package com.journal.App.Service;
import com.journal.App.Entity.JournalEntity;
import com.journal.App.Entity.User;
import com.journal.App.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository jer;

    @Autowired
    private UserEntryService uer;
    public List<JournalEntity> saveJournalEntry(JournalEntity j,String userName)
    {
        User user=uer.findByUserName(userName);
       JournalEntity jj=jer.save(j);
       user.getJournalEntries().add(jj);
       uer.save(user);
       return jer.findAll();
    }
    public List<JournalEntity> save(JournalEntity j)
    {
        jer.save(j);
        return jer.findAll();
    }
    public List<JournalEntity> getallentrys()
    {
        return jer.findAll();
    }

    public JournalEntity getone(ObjectId id)
    {
        Optional<JournalEntity> h= jer.findById(id);
        if(h.isPresent())
        {
            return h.get();
        }
        return null;
    }

    public boolean deleteJournal(ObjectId id,String userName)
    {
        Optional<JournalEntity> h= jer.findById(id);
        User user=uer.findByUserName(userName);
        if(h.isPresent())
        {
            user.getJournalEntries().removeIf(x->x.getId().equals(id));
            uer.save(user);
            jer.deleteById(id);
            return true;
        }
        return false;
    }
}
