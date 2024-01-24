package dao;

import entity.Courier;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourierServiceDb {

    private static Connection connection;

    static {
        try {
			connection = DBConnUtil.getConnection(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private List<Courier> courierOrders;

    // Method to insert a new courier order into the database
    public boolean placeOrder(Courier courier) {
        String sql = "INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courier.getSenderName());
            statement.setString(2, courier.getSenderAddress());
            statement.setString(3, courier.getReceiverName());
            statement.setString(4, courier.getReceiverAddress());
            statement.setDouble(5, courier.getWeight());
            statement.setString(6, courier.getStatus());
            statement.setString(7, courier.getTrackingNumber());
            statement.setDate(8, (Date) courier.getDeliveryDate());
            
            statement.executeUpdate();

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
            return false;
        }
    }

    // Method to update the status of a courier order in the database
    public boolean updateCourierStatus(String trackingNumber, String newStatus) {
        String sql = "UPDATE Courier SET Status = ? WHERE TrackingNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setString(2, trackingNumber);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
            return false;
        }
    }

    // Method to retrieve the delivery history of a specific parcel from the database
    public List<String> getDeliveryHistory(String trackingNumber) {
        List<String> deliveryHistory = new ArrayList<>();
        String sql = "SELECT Status, DeliveryDate FROM Courier WHERE TrackingNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trackingNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String status = resultSet.getString("Status");
                Date deliveryDate = resultSet.getDate("DeliveryDate");
                String entry = "Status: " + status + ", Delivery Date: " + deliveryDate;
                deliveryHistory.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }
        return deliveryHistory;
    }
//
//	public String getOrderStatusByTrackingNumber(String trackingNumber) {
//		// TODO Auto-generated method stub
//		return null;
//	}
    public CourierServiceDb() {
        this.courierOrders = courierOrders;
    }

//    public String getOrderStatusByTrackingNumber(String trackingNumber) {
//        // Search for the order with the given trackingNumber
//    	String sql = "SELECT * FROM Courier WHERE TrackingNumber = ?";
//        for (Courier courier : courierOrders) {
//            if (courier.getTrackingNumber().equals(trackingNumber)) {
//                return courier.getStatus();
//            }
//        }
//
//        // If the trackingNumber is not found, you might return a default or handle it accordingly
//        return "Tracking number not found: " + trackingNumber;
//    }
    public Courier getCourierByTrackingNumber(String trackingNumber) {
        String sql = "SELECT * FROM Courier WHERE TrackingNumber = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, trackingNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Create and return a Courier object based on the result set
                	return mapResultSetToCourier(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
        }

        return null; // Return null if the tracking number is not found
    }
private Courier mapResultSetToCourier(ResultSet resultSet) throws SQLException {
    // Extract values from the result set and create a Courier object
    String trackingNumber = resultSet.getString("TrackingNumber");
    String senderName = resultSet.getString("SenderName");
    String senderAddress = resultSet.getString("SenderAddress");
    String receiverName = resultSet.getString("ReceiverName");
    String receiverAddress = resultSet.getString("ReceiverAddress");
    double weight = resultSet.getDouble("Weight");

    // Include other fields as needed

    // Create and return a Courier object
    return new Courier(trackingNumber, senderName, senderAddress, receiverName, receiverAddress, weight);
}

	//	public boolean cancelOrderByTrackingNumber(String trackingNumber) {
//		// TODO Auto-generated method stub
//		return false;
//	}
    public void CourierServiceDb1(List<Courier> courierOrders) {
        this.courierOrders = courierOrders;
    }

    public boolean cancelOrderByTrackingNumber(String trackingNumber) {
        Iterator<Courier> iterator = courierOrders.iterator();

        while (iterator.hasNext()) {
            Courier courier = iterator.next();
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                // Assuming you have a status field in Courier class
                if (courier.getStatus().equals("Delivered")) {
                    // You may want to handle cancellation differently for delivered orders
                    return false; // Cannot cancel a delivered order
                }

                // Remove the order from the list since it's canceled
                iterator.remove();
                return true; // Order canceled successfully
            }
        }

        // If the trackingNumber is not found, return false
        return false;
    }
    // Additional methods for database interactions can be added here
}