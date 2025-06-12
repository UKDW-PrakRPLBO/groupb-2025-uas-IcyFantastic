package org.uas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnectionManager {
    private static final String DB_URL = "jdbc:sqlite:dbuas.db";
    private static Connection connection;
    private DBConnectionManager() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection =  DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                throw new RuntimeException("Gagal Terhubung Dengan Database", e);
            }
        }
        return connection;
    }
}

