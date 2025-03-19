package org.example.service;
import org.example.entity.Employee;

import java.util.List;

public interface DBEmployeeService {
    int addNewEmployee(Employee employee);

    List<Employee> getAllEmployees();

    int updateEmployee(Employee employee);

    int deleteEmployee(long empId);
}