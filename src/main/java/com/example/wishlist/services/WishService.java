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

    public void addWish(int wishlistID, Wish wish) {

        int position = WISH_REPO.getAmountOfWishes(wishlistID) + 1; // get the
        wish.setPosition(position);
        wish.setWishlistID(wishlistID);

        WISH_REPO.createWish(wish);
    }

    public Wish getWish(int wishlistID, int wishPosition) {
        return WISH_REPO.getWish(wishlistID, wishPosition);
    }

    public void updateWish(int wishlistID, int wishPosition, Wish wish) {
        Wish oldWish = WISH_REPO.getWish(wishlistID, wishPosition);

        if (!wish.getTitle().equals(oldWish.getTitle())) {
            WISH_REPO.updateTitle(wishlistID, wishPosition, wish.getTitle());
        }

        if (!wish.getLink().equals(oldWish.getLink())) {
            WISH_REPO.updateLink(wishlistID, wishPosition, wish.getLink());
        }

        if (!wish.getDescription().equals(oldWish.getDescription())) {
            WISH_REPO.updateDescription(wishlistID, wishPosition, wish.getDescription());
        }
    }

    public void deleteWish(int wishlistID, int wishPosition) {
        WISH_REPO.deleteWish(wishlistID, wishPosition);
    }

    public void deleteWishes(int wishlistID) {
        WISH_REPO.deleteWishes(wishlistID);
    }

    public void setReservationStatus(int userID, Wishlist wishlist) {
        for (Wish wish : wishlist.getWishes()) {
            wish.setReserverStatus(userID);
        }
    }

    public void reserveWish(int wishlistID, int wishPosition, int userID) {
        Wish wish = WISH_REPO.getWish(wishlistID, wishPosition);

        if (wish.getReserverID() == 0) {
            WISH_REPO.updateReserverID(wishlistID, wishPosition, userID);
        }
        if (wish.getReserverID() == userID) {
            WISH_REPO.updateReserverID(wishlistID, wishPosition, 0);
        }
    }
}
