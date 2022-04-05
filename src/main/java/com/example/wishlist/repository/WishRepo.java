package com.example.wishlist.repository;

import com.example.wishlist.models.Wish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishRepo {
    private static WishRepo INSTANCE = new WishRepo();
    private final ConnectionManager CONNECTION_MANAGER = ConnectionManager.getInstance();

    private WishRepo() {

    }

    public static WishRepo getINSTANCE() {
        return INSTANCE;
    }

    public List<Wish> getWishes(int wishlistID) {
        Connection con = CONNECTION_MANAGER.getConnection();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM wishes " +
                    "WHERE `WishlistID` = " + wishlistID + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Wish> wishes = new ArrayList<>();
        try {
            while(rs.next()){
                       wishlistID  = rs.getInt("WishlistID");
                int    position    = rs.getInt("position");
                String title       = rs.getString("title");
                String description = rs.getString("description");
                String link        = rs.getString("link");
                int    reserverID  = rs.getInt("reserverID");

                wishes.add(new Wish(wishlistID, position, title, description, link, reserverID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishes;
    }

    public int getAmountOfWishes(int wishlistID) {
        Connection con = CONNECTION_MANAGER.getConnection();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT MAX(position) AS MaxPositionOfWishes FROM wishes " +
                                           "WHERE WishlistID = " + wishlistID + "; ");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while(rs.next()){
                return rs.getInt("MaxPositionOfWishes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void createWish(Wish wish) {
        Connection con = CONNECTION_MANAGER.getConnection();

        String insertSQL = "INSERT INTO wishes(`WishlistID`, `position`, `title`, `description`, `link`) " +
                "VALUES (?, ?, ?, ?, ?);";

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(insertSQL);
            stmt.setInt(1, wish.getWishlistID());
            stmt.setInt(2, wish.getPosition());
            stmt.setString(3, wish.getTitle());
            stmt.setString(4, wish.getDescription());
            stmt.setString(5, wish.getLink());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
