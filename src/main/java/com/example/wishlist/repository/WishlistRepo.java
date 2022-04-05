package com.example.wishlist.repository;

import com.example.wishlist.models.Wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistRepo {
    private static final WishlistRepo INSTANCE = new WishlistRepo();
    private final ConnectionManager CONNECTION_MANAGER = ConnectionManager.getInstance();



    private WishlistRepo() {

    }

    public static WishlistRepo getINSTANCE() {
        return INSTANCE;
    }

    public int createWishlist(int userID, Wishlist wishlist) {
        Connection con = CONNECTION_MANAGER.getConnection();

        String insertSQL = "INSERT INTO wishlists(`name`, `UserID`) " +
                "VALUES (?, ?);";
        String selectSQL = "SELECT * FROM wishlists " +
                "WHERE `UserID` = '" + userID +  "' AND name = '" + wishlist.getName() + "';";

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(insertSQL);
            stmt.setString(1, wishlist.getName());
            stmt.setInt(2, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(selectSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs.next();
            return rs.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return -1;
    }
}
