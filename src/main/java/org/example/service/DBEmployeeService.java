package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.DBConfig;
import org.example.entity.Employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBEmployeeService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void addNewEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = objectMapper.readValue(req.getInputStream(), Employee.class);
        String sql = "Insert into employee (name, age, phone) values(?,?,?)";
        try (Connection conn = DBConfig.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setInt(2, emp.getAge());
            stmt.setString(3, emp.getPhone());

            int rowsEffected = stmt.executeUpdate();
            if (rowsEffected > 0) {
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

    public void getAllEmployees(HttpServletResponse resp) throws IOException {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "Select * from employee;";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery();) {
            while (resultSet.next()) {
                Employee emp = new Employee();
                emp.setEmpId(resultSet.getLong("empId"));
                emp.setName(resultSet.getString("name"));
                emp.setAge(resultSet.getInt("age"));
                emp.setPhone(resultSet.getString("phone"));
                employeeList.add(emp);
            }
            resp.setContentType("application/json");
            if (employeeList.isEmpty()) {
                resp.getWriter().write("{\"message\": \"No employees found\"}");
            } else {
                resp.getWriter().write(objectMapper.writeValueAsString(employeeList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    public void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = objectMapper.readValue(req.getInputStream(), Employee.class);
        String sql = "update employee set name = ?, age =?, phone =? where empId= ?";

        try (Connection conn = DBConfig.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setInt(2, emp.getAge());
            stmt.setString(3, emp.getPhone());
            stmt.setLong(4, emp.getEmpId());

            int rowsEffected = stmt.executeUpdate();
            if (rowsEffected > 0) {
                resp.setContentType("text/plain");
                resp.getWriter().write("Updated " + rowsEffected + " rows");
            } else {
                resp.setContentType("text/plain");
                resp.getWriter().write("Employee with empId " + emp.getEmpId() + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    public void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long empId = Long.parseLong(req.getParameter("empId"));
        String sql = "delete from employee where empId=?";
        try (Connection conn = DBConfig.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, empId);
            int rowEffected = stmt.executeUpdate();
            if (rowEffected > 0) {
                resp.setContentType("text/plain");
                resp.getWriter().write("Deleted " + rowEffected + " rows");
            } else {
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