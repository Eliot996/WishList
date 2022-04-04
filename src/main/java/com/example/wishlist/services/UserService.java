package com.example.wishlist.services;

import com.example.wishlist.models.User;
import com.example.wishlist.repository.UserRepo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private final UserRepo USER_REPO = UserRepo.getInstance();

    public User getUser(int id) {
        return USER_REPO.getUser(id);
    }

    public User createUser(String name, String email, String password) {
        String salt = generateSalt();

        User newUser = new User(-1, // marked to signify it is temporary id
                name,
                email,
                hashPassword(password, salt),
                salt);

        return USER_REPO.createUser(newUser);
    }

    private String hashPassword(String password, String salt) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] encodedHash = digest.digest((password+salt).getBytes(StandardCharsets.UTF_8));


        return bytesToHex(encodedHash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String generateSalt() { // TODO: 04/04/2022 implement
        return "0123456789abcdef";
    }
}
