package org.example.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.DBEmployeeService;
import org.example.service.InMemoryEmployeeService;

import java.io.IOException;

public class EmployeeServlet extends HttpServlet {
    private static final InMemoryEmployeeService inMemoryService = new InMemoryEmployeeService();
    private static final DBEmployeeService dbService = new DBEmployeeService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        inMemoryService.addNewEmployee(req, resp);
        dbService.addNewEmployee(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        inMemoryService.getListOfEmployees(resp);
        dbService.getAllEmployees(resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        inMemoryService.updateEmployee(req, resp);
        dbService.updateEmployee(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        inMemoryService.deleteEmployee(req, resp);
        dbService.deleteEmployee(req, resp);
    }
}