package com.kost.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/// ini berfungsi sebagai halaman yang mengkoneksikan database SQL nya

public class DBConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:8889/manajemen_kost";
    private static final String USER = "root";
    private static final String PASS = "root"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}