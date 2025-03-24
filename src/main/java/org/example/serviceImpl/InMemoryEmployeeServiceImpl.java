package org.example.serviceImpl;

import org.example.entity.Employee;
import org.example.service.EmployeeService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryEmployeeServiceImpl implements EmployeeService {
    private static List<Employee> listOfEmployees = new ArrayList<>();

    public long addNewEmployee(Employee employee) {
        employee.setEmpId(Employee.idCounter++);
        listOfEmployees.add(employee);
        return employee.getEmpId();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return listOfEmployees;
    }

    public long updateEmployee(Employee updatedEmp) {
        for (Employee emp : listOfEmployees) {
            if (emp.getEmpId() == updatedEmp.getEmpId()) {
                emp.setName(updatedEmp.getName());
                emp.setAge(updatedEmp.getAge());
                emp.setPhone(updatedEmp.getPhone());
                return updatedEmp.getEmpId();
            }
        }
        return -1;
    }

    public long deleteEmployee(long empId) {
        Iterator<Employee> iterator = listOfEmployees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getEmpId() == empId) {
                iterator.remove();
                return empId;
            }
        }
        return -1;
    }
}