package com.hms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "@Shreya1502*";

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Register JDBC driver (optional for newer versions of JDBC)
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC driver not found", e);
        }
        return connection;
    }
}
