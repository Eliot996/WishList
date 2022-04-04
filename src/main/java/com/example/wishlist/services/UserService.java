package com.example.wishlist.services;

import com.example.wishlist.models.User;
import com.example.wishlist.repository.UserRepo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UserService {
    private final UserRepo USER_REPO = UserRepo.getInstance();

    private final Random random = new Random();
    private final String PEPPER_CHARACTERS = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";

    public User getUser(int id) {
        return USER_REPO.getUser(id);
    }

    public User createUser(String name, String email, String password) {
        String salt = generateSalt();
        String pepper = String.valueOf(
                PEPPER_CHARACTERS.charAt(
                        random.nextInt(PEPPER_CHARACTERS.length())));

        User newUser = new User(-1, // marked to signify it is temporary id
                name,
                email,
                hashPassword(pepper, password, salt),
                salt);

        return USER_REPO.createUser(newUser);
    }

    public String login(String email, String password) {
        // Get the user from the database
        User user = USER_REPO.getUser(email);

        if (user == null) {
            return null;
        }

        if (checkPassword(user.getPassword(), user.getSalt(), password)) {
            String token = generateToken();

            USER_REPO.createToken(user.getID(), token);

            return token;
        } else {
            return null;
        }
    }

    private boolean checkPassword(String userPassword, String userSalt, String passwordToCheck) {
        String hashToCheck;

        for (int i = 0; i < PEPPER_CHARACTERS.length(); i++) {
            System.out.println("checking: " + PEPPER_CHARACTERS.substring(i, i+1));
            hashToCheck = hashPassword(PEPPER_CHARACTERS.substring(i, i+1),
                         passwordToCheck,
                         userSalt);
            if (hashToCheck.equals(userPassword)) {
                return true;
            }
        }

        return false;
    }

    private String hashPassword(String pepper, String password, String salt) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] encodedHash = digest.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));


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

    private String generateSalt() {
        StringBuilder salt = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            salt.append(Character.toChars(random.nextInt(94) + 32));
        }
        return salt.toString();
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < 255; i++) {
            token.append(Character.toChars(random.nextInt(94) + 32));
        }
        return token.toString();
    }
}
