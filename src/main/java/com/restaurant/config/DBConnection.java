package com.restaurant.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "rajeev@123";

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            Properties props = new Properties();
            props.setProperty("user", USERNAME);
            props.setProperty("password", PASSWORD);

            this.connection = DriverManager.getConnection(URL, props);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection.isClosed()) {
                Properties props = new Properties();
                props.setProperty("user", USERNAME);
                props.setProperty("password", PASSWORD);
                connection = DriverManager.getConnection(URL, props);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database connection", e);
        }
        return connection;
    }
}
