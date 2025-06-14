package org.example.serviceImpl;

import org.example.config.DBConfig;
import org.example.entity.Employee;
import org.example.service.EmployeeService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBEmployeeServiceImpl implements EmployeeService {

    public long addNewEmployee(Employee employee) {
        String sql = "Insert into employee (name, age, phone) values(?,?,?)";
        long rowsEffected = -1;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setInt(2, employee.getAge());
            stmt.setString(3, employee.getPhone());
            rowsEffected = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsEffected;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "Select * from employee;";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                Employee emp = new Employee();
                emp.setEmpId(resultSet.getLong("empId"));
                emp.setName(resultSet.getString("name"));
                emp.setAge(resultSet.getInt("age"));
                emp.setPhone(resultSet.getString("phone"));
                employeeList.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public long updateEmployee(Employee employee) {
        String sql = "update employee set name = ?, age =?, phone =? where empId= ?";
        long rowsEffected = -1;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setInt(2, employee.getAge());
            stmt.setString(3, employee.getPhone());
            stmt.setLong(4, employee.getEmpId());
            rowsEffected = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsEffected;
    }

    public long deleteEmployee(long empId) {
        String sql = "delete from employee where empId=?";
        long rowsEffected = -1;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, empId);
            rowsEffected = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsEffected;
    }
}