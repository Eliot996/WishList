package com.example.wishlist.services;

import com.example.wishlist.models.Wishlist;
import com.example.wishlist.repository.WishlistRepo;

import java.util.List;

public class WishlistService {
    private final WishlistRepo WISHLIST_REPO = WishlistRepo.getINSTANCE();

    public int createWishlist(int userID, Wishlist wishlist) {
        return WISHLIST_REPO.createWishlist(userID, wishlist);
    }

    public Wishlist getWishlistInfo(int wishlistID) {
        return WISHLIST_REPO.getWishlistInfo(wishlistID);
    }

    public void update(Wishlist oldWishlist, Wishlist wishlist) {
        if (!oldWishlist.getName().equals(wishlist.getName())) {
            WISHLIST_REPO.updateName(oldWishlist, wishlist.getName());
        }
    }

    public List<Wishlist> getAllWishlistsFromUser(int userID) {
        return WISHLIST_REPO.getAllWishlistsFromUser(userID);
    }
}
