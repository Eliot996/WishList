package com.example.wishlist.models;

public class Wish {
    private int wishlistID;
    private int position;
    private String title;
    private String description;
    private String link;
    private int reserver;


    public Wish(){
    }


    public Wish(int wishlistID, int position, String title, String description, String link, int reserver) {
        this.wishlistID = wishlistID;
        this.position = position;
        this.title = title;
        this.description = description;
        this.link = link;
        this.reserver = reserver;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getReserver() {
        return reserver;
    }

    public void setReserver(int reserver) {
        this.reserver = reserver;
    }
}