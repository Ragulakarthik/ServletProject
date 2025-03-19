package org.example.serviceImpl;

import org.example.entity.Employee;
import org.example.service.InMemoryEmployeeService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class InMemoryEmployeeServiceImpl implements InMemoryEmployeeService {

    public void addNewEmployee(Employee employee, List<Employee> listOfEmployees) {
        employee.setEmpId(Employee.idCounter++);
        listOfEmployees.add(employee);
    }

    public boolean updateEmployee(Employee updatedEmp, List<Employee> listOfEmployees) {
        for (Employee emp : listOfEmployees) {
            if (emp.getEmpId() == updatedEmp.getEmpId()) {
                emp.setName(updatedEmp.getName());
                emp.setAge(updatedEmp.getAge());
                emp.setPhone(updatedEmp.getPhone());
                return true;
            }
        }
        return false;
    }

    public boolean deleteEmployee(long empId, List<Employee> listOfEmployees) throws IOException {
        Iterator<Employee> iterator = listOfEmployees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getEmpId() == empId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}