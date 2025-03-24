package org.example.service;
import org.example.entity.Employee;

import java.util.List;

public interface EmployeeService {
    long addNewEmployee(Employee employee);

    List<Employee> getAllEmployees();

    long updateEmployee(Employee employee);

    long deleteEmployee(long empId);
}