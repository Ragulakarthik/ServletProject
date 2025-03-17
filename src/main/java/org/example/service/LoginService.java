package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.config.DBConfig;
import org.example.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean checkInDataBase(User user, HttpServletResponse resp) throws IOException {
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
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
        return false;
    }

    public void loginAuthentication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sessionCheck = req.getSession(false);
        if (sessionCheck != null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Please logout before login to another account");
            return;
        }
        User user = objectMapper.readValue(req.getInputStream(), User.class);
        if (checkInDataBase(user, resp)) {
            HttpSession session = req.getSession();
            session.setAttribute("userName", user.getUserName());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Session Created Successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("User Not Found");
        }
    }
}