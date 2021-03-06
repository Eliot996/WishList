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
        String pepper = generatePepper();

        User newUser = new User(-1, // marked to signify it is temporary id
                name,
                email,
                hashPassword(pepper, password, salt),
                salt);

        return USER_REPO.createUser(newUser);
    }

    private String generatePepper() {
        return String.valueOf(
                PEPPER_CHARACTERS.charAt(
                        random.nextInt(PEPPER_CHARACTERS.length())));
    }

    public String login(String email, String password) {
        // Get the user from the database
        User user = USER_REPO.getUser(email);

        if (user == null) {
            return null;
        }

        if (checkPassword(user.getPassword(), user.getSalt(), password)) {
            // todo: check if there is a token already
            String token = USER_REPO.getTokenFromUserID(user.getID());

            if (token == null) {
                token = generateToken();

                USER_REPO.createToken(user.getID(), token);
            }

            return token;
        } else {
            return null;
        }
    }

    private boolean checkPassword(String userPassword, String userSalt, String passwordToCheck) {
        String hashToCheck;

        for (int i = 0; i < PEPPER_CHARACTERS.length(); i++) {
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
            token.append(Character.toChars(PEPPER_CHARACTERS.charAt(random.nextInt(PEPPER_CHARACTERS.length()))));
        }


        return token.toString();
    }

    public int getUserID(String token) {
        return USER_REPO.getUserIDFromToken(token);
    }

    public void breakToken(int userID) {
        USER_REPO.removeToken(userID);
    }

    public void updateUser(User user, int userID) {
        User oldUser = USER_REPO.getUser(userID);

        if (!user.getName().equals(oldUser.getName())) {
            System.out.println("change name");
            USER_REPO.updateName(userID, user.getName());
        }

        if (!user.getEmail().equals(oldUser.getEmail())) {
            System.out.println("change email");
            USER_REPO.updateEmail(userID, user.getEmail());
        }

        if (!user.getPassword().equals("")) {
            System.out.println("change password");
            String salt = generateSalt();
            String hash = hashPassword(generatePepper(), user.getPassword(), salt);
            USER_REPO.updatePassword(userID, hash, salt);
        }
    }
}
