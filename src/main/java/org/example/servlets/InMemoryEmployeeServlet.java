package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Employee;
import org.example.serviceImpl.InMemoryEmployeeServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryEmployeeServlet extends HttpServlet {
    private static final InMemoryEmployeeServiceImpl inMemoryServiceImpl = new InMemoryEmployeeServiceImpl();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<Employee> listOfEmployees = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Employee employee = objectMapper.readValue(req.getInputStream(), Employee.class);
            inMemoryServiceImpl.addNewEmployee(employee, listOfEmployees);
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(employee));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(listOfEmployees));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Employee updatedEmp = objectMapper.readValue(req.getInputStream(), Employee.class);
            if (inMemoryServiceImpl.updateEmployee(updatedEmp, listOfEmployees)) {
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(updatedEmp));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"message\": \"Employee not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long empId = Long.parseLong(req.getParameter("empId"));
            if (inMemoryServiceImpl.deleteEmployee(empId, listOfEmployees)) {
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\": \"Employee deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"message\": \"Employee not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}