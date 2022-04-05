package com.example.wishlist.repository;

import com.example.wishlist.models.Wish;
import com.example.wishlist.models.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class DummyWishlistRepo
{
    private ArrayList<Wishlist> listOfWishlists = new ArrayList<>();
    private ArrayList<Wish> listOfWishes = new ArrayList<>();

    public DummyWishlistRepo(){
        fillWishlist();
    }

    public void fillWishlist(){
        for (int i = 0; i < 10; i ++){
            listOfWishlists.add(new Wishlist(i, "name"+i, listOfWishes, -1));
        }
    }

    public List<Wishlist> getListOfWishlists(){
        return listOfWishlists;
    }

}
