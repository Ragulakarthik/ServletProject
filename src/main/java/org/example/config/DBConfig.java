package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConfig {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    static {
        Properties properties = new Properties();
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
        URL = properties.getProperty("db.URL");
        USER = properties.getProperty("db.USER");
        PASSWORD = properties.getProperty("db.PASSWORD");
        initializeDatabase();
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!", e);
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            if (!isTableExists(conn)) {
                createEmployeeTable(conn);
                System.out.println("Table 'employee' created successfully.");
            } else {
                System.out.println("Table 'employee' already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isTableExists(Connection conn) throws SQLException {
        String checkTableSQL = "SHOW TABLES LIKE 'employee'";
        try (PreparedStatement stmt = conn.prepareStatement(checkTableSQL);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        }
    }

    private static void createEmployeeTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE Employee.employee ("
                + "empId BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "age INT, "
                + "phone VARCHAR(20))";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }
}