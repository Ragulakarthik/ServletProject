package org.example.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.DBConfig;
import org.example.entity.User;
import org.example.service.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServiceImpl implements LoginService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public boolean loginAuthenticate(User user){
        String sql = "select * from user where userName=? and password=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}