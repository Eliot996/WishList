package com.example.wishlist.models;


import java.util.List;

public class Wishlist {
    private int ID;
    private String name;
    private List<Wish> wishes = null;
    private int userID;

    public Wishlist(){

    }

    public Wishlist(int ID, String name, List<Wish> wishes, int userID) {
        this.ID = ID;
        this.name = name;
        this.wishes = wishes;
        this.userID = userID;
    }

    public Wishlist(int ID, String name, int userID) {
        this.ID = ID;
        this.name = name;
        this.userID = userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}