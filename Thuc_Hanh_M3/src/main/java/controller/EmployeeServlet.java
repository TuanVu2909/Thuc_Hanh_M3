package controller;

import service.DepartmentService;
import service.EmployeeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private final DepartmentService departmentService = DepartmentService.getInstance();
    private final EmployeeService employeeService = EmployeeService.getInstance();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createGet(request, response);
                break;
            case "update":
                updateGet(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                findAdd(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createPost(request, response);
                break;
            case "update":
                updatePost(request, response);
                break;
            case "search":
                search(request, response);
                break;
        }
    }
    private void findAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employee/home.jsp");
        request.setAttribute("employee", employeeService.findAll());
        requestDispatcher.forward(request, response);
    }

    private void createGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("department", departmentService.findAll());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employee/create.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int departmentId = Integer.parseInt(request.getParameter("department"));
        if (employeeService.checkById(departmentId)) {
            employeeService.save(request);
            response.sendRedirect("EmployeeServlet");
        } else {
            response.sendRedirect("404.jsp");
        }
    }

    private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (employeeService.checkById(id)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employee/update.jsp");
            request.setAttribute("employee", employeeService.getById(id));
            request.setAttribute("department", departmentService.findAll());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("404.jsp");
        }
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departmentId = Integer.parseInt(request.getParameter("department"));
        int id = Integer.parseInt(request.getParameter("id"));
        if (employeeService.checkById(id) && departmentService.checkById(departmentId)) {
            employeeService.save(request);
            response.sendRedirect("EmployeeServlet");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employeeService.delete(request);
        response.sendRedirect("EmployeeServlet");
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {;
        request.setAttribute("employee", employeeService.searchName(request));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("employee/home.jsp");
        requestDispatcher.forward(request, response);
    }
    }

