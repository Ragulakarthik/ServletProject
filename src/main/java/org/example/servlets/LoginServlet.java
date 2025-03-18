package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.entity.User;
import org.example.serviceImpl.LoginServiceImpl;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    LoginServiceImpl loginService = new LoginServiceImpl();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Please logout before login to another account");
                return;
            }
            User user = objectMapper.readValue(req.getInputStream(), User.class);
            if (loginService.loginAuthenticate(user)) {
                session = req.getSession();
                session.setAttribute("userName", user.getUserName());
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Session Created Successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("User Not Found");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}