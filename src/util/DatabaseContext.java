//package util;
//
//import java.sql.Connection;
//
//public class DatabaseContext {
//
//    private static final String DATABASE_PROPERTIES_FILE = "db.properties";
//
//    // Ensure that the connection is established only once
//    private static Connection connection;
//
//    static {
//        initialize();
//    }
//
//    private static void initialize() {
//        if (connection == null) {
//            // Establish a connection to the database
//            connection = DBConnUtil.getConnection();
//        }
//    }
//
//    public static Connection getConnection() {
//        return connection;
//    }
//}
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseConnectionException;

public class DatabaseContext {
	public static Connection getConnection() throws DatabaseConnectionException {
	    try {
	        String url = "jdbc:mysql://localhost:3306/couriermanagment?useSSL=false&serverTimezone=UTC";
	        String username = "root";
	        String password = "K@13tubh";

	        return DriverManager.getConnection(url, username, password);
	    } catch (SQLException e) {
	        throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
	    }
	}
}