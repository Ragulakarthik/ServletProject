package org.example.service;

import org.example.entity.Employee;

import java.io.IOException;
import java.util.List;

public interface InMemoryEmployeeService {
    void addNewEmployee(Employee employee, List<Employee> listOfEmployees) throws IOException;

    boolean updateEmployee(Employee updatedEmp, List<Employee> listOfEmployees) throws IOException;

    boolean deleteEmployee(long empId, List<Employee> listOfEmployees) throws IOException;
}