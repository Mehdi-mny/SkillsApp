package com.example.skillsapp;

public class User {

    private String uid;
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String domain;
    private String description;

    public User() {

    }

    public User(String uid, String name, String email, String phone, String dob, String domain, String description) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.domain = domain;
        this.description = description;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
