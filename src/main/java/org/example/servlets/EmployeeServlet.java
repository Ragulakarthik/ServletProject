package org.example.servlets;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    private static final List<List<String>> listOfEmp = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String phone = req.getParameter("phone");
        if(name!=null && age!=null && phone !=null){
            List<String> emp=new ArrayList<>();
            emp.add(name);
            emp.add(age);
            emp.add(phone);
            listOfEmp.add(emp);
        }
        resp.sendRedirect("employee");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Employees</title></head><body>");
        out.println("<h2>Employee List</h2>");
        out.println("<table><tr><th>Name</th><th>Age</th><th>Phone</th></tr>");
        for (List<String> emp : listOfEmp) {
            out.println("<tr><td>" + emp.get(0) + "</td><td>" + emp.get(1) + "</td><td>" + emp.get(2) + "</td></tr>");
        }
        out.println("</table>");
        out.println("<br><a href='addEmployee.html'>Add More Employees</a>");
        out.println("</body></html>");
    }
}
