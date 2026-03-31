package com.familycircle.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.familycircle.app.model.Contact;
import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contacts WHERE name LIKE :query OR relation LIKE :query OR city LIKE :query OR state LIKE :query OR email LIKE :query ORDER BY name ASC")
    LiveData<List<Contact>> searchContacts(String query);
}