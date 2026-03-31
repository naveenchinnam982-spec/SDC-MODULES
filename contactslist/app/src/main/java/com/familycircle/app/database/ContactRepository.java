package com.familycircle.app.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.familycircle.app.model.Contact;
import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<List<Contact>> searchContacts(String query) {
        return contactDao.searchContacts(query);
    }

    public void insert(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.insert(contact);
        });
    }

    public void update(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.update(contact);
        });
    }

    public void delete(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> contactDao.delete(contact));
    }
}