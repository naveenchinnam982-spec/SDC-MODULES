package com.familycircle.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String relation;
    private String phone;
    private String email;
    private String city;
    private String state;
    private String birthday;
    private String notes;
    private String imagePath;
    private long lastCalled;
    private double latitude;
    private double longitude;

    public Contact(String name, String relation, String phone, String email, String city, String state, String birthday, String notes, String imagePath, double latitude, double longitude) {
        this.name = name;
        this.relation = relation;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.state = state;
        this.birthday = birthday;
        this.notes = notes;
        this.imagePath = imagePath;
        this.lastCalled = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public long getLastCalled() { return lastCalled; }
    public void setLastCalled(long lastCalled) { this.lastCalled = lastCalled; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}