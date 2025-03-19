package org.example.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class AppListener implements ServletContextListener, HttpSessionListener, ServletRequestListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.print("Session Destroyed " + se.getSession().getId());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Request intialized" + sre.getServletRequest().getRequestId());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request completed / Destroyed" + sre.getServletRequest().getRequestId());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application Started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application Destroyed");
    }
}
