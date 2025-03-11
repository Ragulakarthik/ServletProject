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
import java.util.Arrays;
import java.util.List;

public class DBEmployeeService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void addNewEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Employee emp = objectMapper.readValue(req.getInputStream(), Employee.class);
        String sql = "Insert into Employee.employee (empId, name, age, phone) values(?,?,?,?)";

        try (Connection conn = DBConfig.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, Employee.idCounter++);
            stmt.setString(2, emp.getName());
            stmt.setInt(3, emp.getAge());
            stmt.setString(4, emp.getPhone());

            int rowsEffected = stmt.executeUpdate();
            if (rowsEffected > 0) {
                resp.setContentType("text/plain");
                resp.getWriter().write("Inserted "+rowsEffected+" rows");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(Arrays.toString(e.getStackTrace()));
        }
    }

    public void getAllEmployees(HttpServletResponse resp) throws IOException {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "Select * from Employee.employee;";
        try (Connection conn = DBConfig.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Employee emp = new Employee();
                emp.setEmpId(resultSet.getLong("empId"));
                emp.setName(resultSet.getString("name"));
                emp.setAge(resultSet.getInt("age"));
                emp.setPhone(resultSet.getString("phone"));
                employeeList.add(emp);
            }
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(employeeList));
        } catch (Exception e) {
            resp.getWriter().write(Arrays.toString(e.getStackTrace()));
        }
    }

    public void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Employee emp = objectMapper.readValue(req.getInputStream(), Employee.class);
        String sql = "update Employee.employee set name = ?, age =?, phone =? where empId= ?";

        try(Connection conn=DBConfig.getConnection()){
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setInt(2, emp.getAge());
            stmt.setString(3, emp.getPhone());
            stmt.setLong(4, emp.getEmpId());

            int rowsEffected = stmt.executeUpdate();
            if(rowsEffected>0){
                resp.setContentType("text/plain");
                resp.getWriter().write("Updated "+rowsEffected+" rows");
            }
        }
        catch (Exception e){
            resp.getWriter().write(Arrays.toString(e.getStackTrace()));
        }
    }

    public void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long empId=Long.parseLong(req.getParameter("empId"));
        String sql="delete Employee.employee where empId=?";
        try(Connection conn=DBConfig.getConnection()){
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setLong(1,empId);
            int rowEffected =stmt.executeUpdate();
            if(rowEffected>0){
                resp.setContentType("text/plain");
                resp.getWriter().write("Deleted "+rowEffected +" rows");
            }
        }
        catch (Exception e){
            resp.getWriter().write(Arrays.toString(e.getStackTrace()));
        }
    }
}