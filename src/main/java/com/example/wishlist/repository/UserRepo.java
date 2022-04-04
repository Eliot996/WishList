package com.example.wishlist.repository;

import com.example.wishlist.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepo {

    private static final UserRepo INSTANCE = new UserRepo();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    private UserRepo() {

    }

    public User createUser(String name, String email, String password, String salt) {
        Connection con = connectionManager.getConnection();

        String insertSQL = "INSERT INTO users(name, email, password, salt)" +
                           "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(insertSQL);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, salt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getUser() {

    }

    public static UserRepo getInstance() {
        return INSTANCE;
    }
}
