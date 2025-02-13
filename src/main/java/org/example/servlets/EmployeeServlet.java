package org.example.servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServlet extends HttpServlet {
    private static final List<Employee> listOfEmp = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = objectMapper.readValue(req.getInputStream(), Employee.class);
        listOfEmp.add(emp);
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(emp));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(listOfEmp));
    }
}