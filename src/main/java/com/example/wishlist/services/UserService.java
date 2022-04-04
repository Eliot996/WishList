package com.example.wishlist.services;

import com.example.wishlist.models.User;
import com.example.wishlist.repository.UserRepo;

public class UserService {
    private final UserRepo USER_REPO = UserRepo.getInstance();

    public User getUser(int id) {
        return USER_REPO.getUser(id);
    }
}
