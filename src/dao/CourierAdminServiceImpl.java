package dao;

import entity.Employee;
import exception.InvalidEmployeeIdException;
import exception.TrackingNumberNotFoundException;

import java.util.List;

public class CourierAdminServiceImpl extends CourierUserServiceImpl implements ICourierAdminService {

    private CourierServiceDb courierServiceDb;

    public CourierAdminServiceImpl() {
        // Assuming CourierUserServiceImpl has a constructor that initializes necessary properties
        super();
        this.courierServiceDb = new CourierServiceDb();
    }

    @Override
    public int addCourierStaff(Employee employee) {
        return ((ICourierAdminService) courierServiceDb).addCourierStaff(employee);
    }

    @Override
    public List<Employee> getAllCourierStaff() {
        return ((ICourierAdminService) courierServiceDb).getAllCourierStaff();
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException {
        return ((ICourierAdminService) courierServiceDb).getEmployeeById(employeeId);
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
		return trackingNumber;
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        return courierServiceDb.cancelOrderByTrackingNumber(trackingNumber);
    }

    // Additional methods for admin-related operations can be added here

    // Ensure to implement other methods from ICourierUserService if needed

}
