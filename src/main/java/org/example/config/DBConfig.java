package org.example.config;

import org.example.entity.DataBase;
import java.sql.*;

public class DBConfig {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DataBase.URL, DataBase.USER, DataBase.PASSWORD);
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