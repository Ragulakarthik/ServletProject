package org.example.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        boolean authorized = false;
        String authHeader = req.getHeader("Authorization");

        if (authHeader != null) {
            System.out.println("Auth header = " + authHeader);
            String[] authHeaderSplit = authHeader.split("\\s");

            System.out.println("authHeaderSplit = ");
            for (int i = 0; i < authHeaderSplit.length; i++) {
                System.out.print(authHeaderSplit[i] + " ");
            }

            for (int i = 0; i < authHeaderSplit.length; i++) {
                String token = authHeaderSplit[i];
                if (token.equalsIgnoreCase("basic")) {
                    String credentials = new String(Base64.getDecoder().decode(authHeaderSplit[i + 1]));
                    System.out.println(credentials);
                    int index = credentials.indexOf(":");
                    if (index != -1) {
                        String userName = credentials.substring(0, index).trim();
                        String passWord = credentials.substring(index + 1).trim();
                        if (userName.equals("admin") && passWord.equals("admin")) {
                            authorized = true;
                        }
                    }
                }
            }
        }
        if (authorized) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}