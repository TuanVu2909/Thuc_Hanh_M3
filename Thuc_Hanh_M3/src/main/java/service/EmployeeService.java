package service;

import DAO.EmployeeDAO;
import model.Department;
import model.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EmployeeService {
    private static EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
    private static EmployeeService employeeService;
    private static DepartmentService departmentService = DepartmentService.getInstance();

    private EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            employeeService = new EmployeeService();
        }
        return employeeService;
    }
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public Employee getById(int id) {
        return employeeDAO.findById(id);
    }

    public void save(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name =  request.getParameter("name");
        String email =  request.getParameter("email");
        String address = request.getParameter("address");
        String phone =  request.getParameter("phone");
        double salary =  Double.parseDouble(request.getParameter("salary"));
        int department_id =  Integer.parseInt(request.getParameter("department"));
        Department department = departmentService.getById(department_id) ;
        if (id != null) {
            Integer idUpdate = Integer.parseInt(id);
            employeeDAO.updateEmployee(new Employee(idUpdate,name,email,address,phone,salary,department));
        } else {
            employeeDAO.addEmployee(new Employee(name,email,address,phone,salary,department));
        }
    }

    public void delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id != null) {
            Integer idDelete = Integer.parseInt(id);
            employeeDAO.deleteById(idDelete);
        }
    }

    public List<Employee> searchName(HttpServletRequest request) {
        String name = request.getParameter("name");
        return employeeDAO.searchName(name);
    }
    public boolean checkById(int id) {
        Employee product = employeeDAO.findById(id);
        return product != null;
    }
}
