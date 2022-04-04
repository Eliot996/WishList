package com.example.wishlist.models;

import java.util.ArrayList;

public class Wishlist {
    private int ID;
    private String name;
    private ArrayList<Wish> wishes = new ArrayList<>();
    private User owner;

    public Wishlist(int ID, String name, ArrayList<Wish> wishes, User owner) {
        this.ID = ID;
        this.name = name;
        this.wishes = wishes;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }
}