package com.example.skillsapp;

public class User {
    private String name;
    private String domain;
    private String description;
    private float rating;

    public User(String name, String domain, String description, float rating) {
        this.name = name;
        this.domain = domain;
        this.description = description;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }
}
