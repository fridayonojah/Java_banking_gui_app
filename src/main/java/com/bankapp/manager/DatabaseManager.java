package com.bankapp.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // Database configuration
    private static final String URL = "jdbc:postgresql://localhost:5436/group_db";
    private static final String USER = "group_user";
    private static final String PASSWORD = "group_password";

    // Static block to ensure driver is loaded once
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver registered.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load PostgreSQL JDBC Driver: " + e.getMessage());
        }
    }

    // Utility method to get a new connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
