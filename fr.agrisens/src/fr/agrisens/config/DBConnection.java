package fr.agrisens.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/agrisens_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("MySQL JDBC Driver not found. Add the connector to the classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}