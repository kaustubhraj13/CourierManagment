package dao;

import entity.Courier;
import entity.Employee;
import exception.InvalidEmployeeIdException;
import exception.TrackingNumberNotFoundException;
import util.DBConnUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class CourierAdminServiceDb extends CourierUserServiceImpl implements ICourierAdminService {

    private List<Courier> courierOrders;

	public CourierAdminServiceDb() {
        super();
    }
    private static Connection connection;
    static {
    	try {
			connection = DBConnUtil.getConnection(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public int addCourierStaff(Employee employee) {
        String sql = "INSERT INTO Employee (Name, Email, ContactNumber, Role, Salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getContactNumber());
            statement.setString(4, employee.getRole());
            statement.setDouble(5, employee.getSalary());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }
        return -1; // Return -1 if insertion fails
    }

    @Override
    public List<Employee> getAllCourierStaff() {
        List<Employee> courierStaffList = new ArrayList<>();
        String sql = "SELECT * FROM Employee WHERE Role = 'Courier Staff'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = mapResultSetToEmployee(resultSet);
                courierStaffList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }
        return courierStaffList;
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException {
        String sql = "SELECT * FROM Employee WHERE EmployeeID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToEmployee(resultSet);
            } else {
                throw new InvalidEmployeeIdException("Employee not found with ID: " + employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
            return null; // Consider a more appropriate handling based on your application's needs
        }
    }

    // Additional methods for admin-related operations can be added here

    // Method to map ResultSet to Employee object
    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeID(resultSet.getInt("EmployeeID"));
        employee.setName(resultSet.getString("Name"));
        employee.setEmail(resultSet.getString("Email"));
        employee.setContactNumber(resultSet.getString("ContactNumber"));
        employee.setRole(resultSet.getString("Role"));
        employee.setSalary(resultSet.getDouble("Salary"));
        return employee;
    }

//	@Override
//	public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}
    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        Courier[] courierOrders = null;
		// Search for the order with the given trackingNumber
        for (Courier courier : courierOrders) {
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                return courier.getStatus();
            }
        }

        // If the trackingNumber is not found, throw an exception
        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }
//	@Override
//	public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
//		// TODO Auto-generated method stub
//		return false;
//	}
    public void CourierUserServiceImpl(List<Courier> courierOrders) {
        this.courierOrders = courierOrders;
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        Iterator<Courier> iterator = courierOrders.iterator();

        while (iterator.hasNext()) {
            Courier courier = iterator.next();
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                // Assuming you have a status field in Courier class
                if (courier.getStatus().equals("Delivered")) {
                    // You may want to handle cancellation differently for delivered orders
                    throw new IllegalStateException("Cannot cancel a delivered order.");
                }

                // Remove the order from the list since it's canceled
                iterator.remove();
                return true; // Order canceled successfully
            }
        }

        // If the trackingNumber is not found, throw an exception
        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }
}