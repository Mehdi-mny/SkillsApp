package com.example.skillsapp;

public class User {
    private String uid;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String dob;
    private float rating; // New field for rating

    // Constructors
    public User() {
        // Default constructor required for Firestore
    }

    public User(String uid, String email, String password, String name, String phone, String dob, float rating) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.rating = rating;
    }

    // Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
