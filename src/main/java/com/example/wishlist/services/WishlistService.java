package com.example.wishlist.services;

import com.example.wishlist.models.Wishlist;
import com.example.wishlist.repository.WishlistRepo;

public class WishlistService {
    private final WishlistRepo WISHLIST_REPO = WishlistRepo.getINSTANCE();

    public int createWishlist(int userID, Wishlist wishlist) {
        return WISHLIST_REPO.createWishlist(userID, wishlist);
    }
}
