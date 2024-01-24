package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.Courier;
import exception.TrackingNumberNotFoundException;
import util.DBConnUtil;

public class CourierUserServiceDb {

    private static Connection connection;

    static {
        try {
			connection = DBConnUtil.getConnection(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Method to get courier details by tracking number
    public Courier getCourierByTrackingNumber(String trackingNumber) throws TrackingNumberNotFoundException {
        String sql = "SELECT * FROM Courier WHERE TrackingNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trackingNumber);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToCourier(resultSet);
            } else {
                throw new TrackingNumberNotFoundException("Courier not found with Tracking Number: " + trackingNumber);
            }
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            return null; // Consider a more appropriate handling based on your application's needs
        }
    }

    // Method to map ResultSet to Courier object
    private Courier mapResultSetToCourier(ResultSet resultSet) throws SQLException {
        Courier courier = new Courier();
        courier.setCourierID(resultSet.getInt("CourierID"));
        courier.setSenderName(resultSet.getString("SenderName"));
        // Map other properties...

        return courier;
    }

    // Additional methods for other courier-related operations can be added here
}