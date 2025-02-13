package org.example.servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployeeServlet extends HttpServlet {
    private static final List<Employee> listOfEmp = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = objectMapper.readValue(req.getInputStream(), Employee.class);
        emp.setEmpId(Employee.idCounter++);
        listOfEmp.add(emp);
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(emp));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(listOfEmp));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee updatedEmp = objectMapper.readValue(req.getInputStream(), Employee.class);
        for (Employee emp : listOfEmp) {
            if (emp.getEmpId() == updatedEmp.getEmpId()) {
                emp.setName(updatedEmp.getName());
                emp.setAge(updatedEmp.getAge());
                emp.setPhone(updatedEmp.getPhone());
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(emp));
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("{\"message\": \"Employee not found\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long empId = Long.parseLong(req.getParameter("empId"));
        Iterator<Employee> iterator = listOfEmp.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getEmpId() == empId) {
                iterator.remove();
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\": \"Employee deleted successfully\"}");
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("{\"message\": \"Employee not found\"}");
    }
}