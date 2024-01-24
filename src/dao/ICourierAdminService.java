package dao;

import entity.Employee;
import exception.InvalidEmployeeIdException;

import java.util.List;

public interface ICourierAdminService extends ICourierUserService {

    int addCourierStaff(Employee employee);

    List<Employee> getAllCourierStaff();

    Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException;

    // Additional methods for admin-related operations can be added here
}