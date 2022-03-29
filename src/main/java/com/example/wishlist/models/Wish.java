package com.example.wishlist.models;

public class Wish {
    private Wishlist wishlistID;
    private int position;
    private String title;
    private String description;
    private String link;
    private User reserver;

    public Wish(Wishlist wishlistID, int position, String title, String description, String link, User reserver) {
        this.wishlistID = wishlistID;
        this.position = position;
        this.title = title;
        this.description = description;
        this.link = link;
        this.reserver = reserver;
    }
}
