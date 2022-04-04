package com.example.wishlist.models;

public class User {
    private int ID;
    private String name;
    private String email;
    private String password;
    private String salt;

    public User(int ID, String name, String email, String password, String salt) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public User() {

    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}