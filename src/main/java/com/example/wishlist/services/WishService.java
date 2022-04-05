package com.example.wishlist.services;

import com.example.wishlist.models.Wish;
import com.example.wishlist.models.Wishlist;
import com.example.wishlist.repository.WishRepo;

import java.util.List;

public class WishService {
    private WishRepo WISH_REPO = WishRepo.getINSTANCE();

    public void populateWishlist(Wishlist wishlist) {
        // get wishes from database
        List<Wish> listOfWishes = WISH_REPO.getWishes(wishlist.getID());

        // bind to wishlist
        wishlist.setWishes(listOfWishes);
    }
}
