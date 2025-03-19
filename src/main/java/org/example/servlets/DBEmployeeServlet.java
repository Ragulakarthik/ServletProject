package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Employee;
import org.example.serviceImpl.DBEmployeeServiceImpl;

import java.io.IOException;
import java.util.List;

public class DBEmployeeServlet extends HttpServlet {
    private static final DBEmployeeServiceImpl dbService = new DBEmployeeServiceImpl();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Employee employee = objectMapper.readValue(req.getInputStream(), Employee.class);
            int rowsEffected = dbService.addNewEmployee(employee);
            if (rowsEffected > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("text/plain");
                resp.getWriter().write("Inserted " + rowsEffected + " rows");
            } else {
                resp.setContentType("text/plain");
                resp.getWriter().write("Inserting failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Employee> employeeList = dbService.getAllEmployees();
            resp.setContentType("application/json");
            if (employeeList.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\": \"No employees found\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(objectMapper.writeValueAsString(employeeList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Employee employee = objectMapper.readValue(req.getInputStream(), Employee.class);
            int rowsEffected = dbService.updateEmployee(employee);
            if (rowsEffected > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("text/plain");
                resp.getWriter().write("Updated " + rowsEffected + " rows");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("text/plain");
                resp.getWriter().write("Employee with empId " + employee.getEmpId() + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long empId = Long.parseLong(req.getParameter("empId"));
            int rowsEffected = dbService.deleteEmployee(empId);
            if (rowsEffected > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("text/plain");
                resp.getWriter().write("Deleted " + rowsEffected + " rows");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("text/plain");
                resp.getWriter().write("Employee with " + empId + " empId not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }
}