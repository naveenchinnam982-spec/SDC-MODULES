package com.familycircle.app.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.familycircle.app.database.ContactRepository;
import com.familycircle.app.model.Contact;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private final ContactRepository repository;
    private final LiveData<List<Contact>> allContacts;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        
        allContacts = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.isEmpty()) {
                return repository.getAllContacts();
            } else {
                return repository.searchContacts("%" + query + "%");
            }
        });
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void insert(Contact contact) {
        repository.insert(contact);
    }

    public void update(Contact contact) {
        repository.update(contact);
    }

    public void delete(Contact contact) {
        repository.delete(contact);
    }
}