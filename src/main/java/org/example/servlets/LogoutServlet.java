package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.LogoutService;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    LogoutService logoutService = new LogoutService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logoutService.logout(req, resp);
    }
}
