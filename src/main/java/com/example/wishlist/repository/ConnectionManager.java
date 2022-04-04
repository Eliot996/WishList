package com.example.wishlist.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private Connection connection;

    private ConnectionManager() {
        connectDB();
    }

    public Connection getConnection() {
        return connection;
    }

    public void connectDB()
    {
        try
        {
            String url = "jdbc:mysql://wishlist-kea-database.mysql.database.azure.com:3306/wishlist?verifyServerCertificate=true&useSSL=true&requireSSL=true";
            connection = DriverManager.getConnection(url,"Main_User@wishlist-kea-database","$UperPassword99");
            System.out.println("Ok, we have a connection.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }
}
