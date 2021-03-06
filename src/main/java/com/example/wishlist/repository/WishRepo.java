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

        CONNECTION_MANAGER.closeConnection();
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

        CONNECTION_MANAGER.closeConnection();
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

        CONNECTION_MANAGER.closeConnection();
    }

    // TODO: 05/04/2022 needs refactoring
    public Wish getWish(int wishlistID, int wishPosition) {
        Connection con = CONNECTION_MANAGER.getConnection();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM wishes " +
                    "WHERE `WishlistID` = " + wishlistID + " AND `position` = " + wishPosition + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Wish wish = null;
        try {
            while(rs.next()){
                wishlistID  = rs.getInt("WishlistID");
                int    position    = rs.getInt("position");
                String title       = rs.getString("title");
                String description = rs.getString("description");
                String link        = rs.getString("link");
                int    reserverID  = rs.getInt("reserverID");

                wish = new Wish(wishlistID, position, title, description, link, reserverID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
        return wish;
    }


    public void updateTitle(int wishlistID, int wishPosition, String title) {
        Connection con = CONNECTION_MANAGER.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE `wishes` " +
                    "SET `title` = '" + title + "' " +
                    "WHERE `WishlistID` = "+ wishlistID +" " +
                        "AND `position` = " + wishPosition + ";");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
    }

    public void updateLink(int wishlistID, int wishPosition, String link) {
        Connection con = CONNECTION_MANAGER.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE `wishes` " +
                    "SET `link` = '" + link + "' " +
                    "WHERE `WishlistID` = "+ wishlistID +" " +
                    "AND `position` = " + wishPosition + ";");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
    }

    public void updateDescription(int wishlistID, int wishPosition, String description) {
        Connection con = CONNECTION_MANAGER.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE `wishes` " +
                    "SET `description` = '" + description + "' " +
                    "WHERE `WishlistID` = "+ wishlistID +" " +
                    "AND `position` = " + wishPosition + ";");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
    }

    public void updateReserverID(int wishlistID, int wishPosition, int userID) {
        Connection con = CONNECTION_MANAGER.getConnection();

        try {
            PreparedStatement abbandon = con.prepareStatement( "SET foreign_key_checks = 0;");
            PreparedStatement stmt = con.prepareStatement("UPDATE `wishes` " +
                    "SET `reserverID` = '" + userID + "' " +
                    "WHERE `WishlistID` = "+ wishlistID +" " +
                    "AND `position` = " + wishPosition + "; ");
            PreparedStatement reset = con.prepareStatement( "SET foreign_key_checks = 1;");

            abbandon.execute();
            stmt.execute();
            reset.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
    }

    public void deleteWish(int wishlistID, int wishPosition) {
        Connection con = CONNECTION_MANAGER.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM `wishes` " +
                    "WHERE `WishlistID` = "+ wishlistID +" " +
                    "AND `position` = " + wishPosition + ";");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
    }

    public void deleteWishes(int wishlistID) {
        Connection con = CONNECTION_MANAGER.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM `wishes` " +
                    "WHERE `WishlistID` = "+ wishlistID +";");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CONNECTION_MANAGER.closeConnection();
    }
}
