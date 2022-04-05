package com.example.wishlist.models;

import java.util.ArrayList;

public class Wishlist {
    private int ID;
    private String name;
    private ArrayList<Wish> wishes = new ArrayList<>();
    private User owner;

    public Wishlist(){

    }

    public Wishlist(int ID, String name, ArrayList<Wish> wishes, User owner) {
        this.ID = ID;
        this.name = name;
        this.wishes = wishes;
        this.owner = owner;
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

    public ArrayList<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(ArrayList<Wish> wishes) {
        this.wishes = wishes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}