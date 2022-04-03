package com.example.wishlist.models;

public class User {
    private int ID;
    private String name;
    private String email;
    private String password;

    public User(int ID, String name, String email, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}