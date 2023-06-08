package service;

import DAO.DepartmentDAO;
import model.Department;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DepartmentService {
    private DepartmentDAO departmentDAO;
    private static DepartmentService departmentService;

    private DepartmentService() {
        departmentDAO = new DepartmentDAO();
    }
    public  static DepartmentService getInstance(){
        if (departmentService == null){
            departmentService = new DepartmentService();
        }
        return departmentService;
    }
    public List<Department> findAll(){
        return departmentDAO.findAll();
    }
    public Department getById(int id){
        return departmentDAO.findById(id);
    }

    public boolean checkById(int id){
        Department department = departmentService.getById(id);
        return department != null;
    }
}
