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

    public User createUser(User user) {
        Connection con = connectionManager.getConnection();

        String insertSQL = "INSERT INTO users(`name`, `email`, `password`, `salt`)" +
                           "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(insertSQL);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getSalt());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TODO: 04/04/2022 add handling of duplicate emails

        ResultSet rs = null;
        try {
            stmt.execute();
            rs = stmt.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return makeUserFromResultSet(rs);
    }

    public int createToken(int userID, String token) {
        Connection con = connectionManager.getConnection();

        String insertSQL = "INSERT INTO `login-tokens`(`UserID`, `token`)" +
                "VALUES (?, ?)";

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(insertSQL);
            stmt.setInt(1, userID);
            stmt.setString(2, token);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public int getUserIDFromToken(String token) {
        Connection con = connectionManager.getConnection();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT UserID FROM `login-tokens` " +
                    "WHERE `token` = '" + token + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (rs != null) {
            try {
                rs.next();
                return rs.getInt("UserID");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
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

        return makeUserFromResultSet(rs);
    }

    private User makeUserFromResultSet(ResultSet rs) {
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

    public User getUser(String email) {
        Connection con = connectionManager.getConnection();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM users " +
                    "WHERE `email` = \'" + email + "\';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return makeUserFromResultSet(rs);

    }

    public static UserRepo getInstance() {
        return INSTANCE;
    }
}
