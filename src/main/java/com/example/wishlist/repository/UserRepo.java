package com.example.wishlist.repository;

import com.example.wishlist.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {

    private static final UserRepo INSTANCE = new UserRepo();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    private UserRepo() {

    }

    public User createUser(String name, String email, String password, String salt) {
        Connection con = connectionManager.getConnection();

        String insertSQL = "INSERT INTO users(`name`, `email`, `password`, `salt`)" +
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

    public User getUser(int id) {
        Connection con = connectionManager.getConnection();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM users " +
                                           "WHERE `ID` = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User user = null;
        try {
            while(rs.next()){
                int userId = rs.getInt("ID");
                String userName = rs.getString("name");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                String userSalt = rs.getString("salt");
                user = new User(userId, userName, userEmail, userPassword, userSalt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static UserRepo getInstance() {
        return INSTANCE;
    }
}
