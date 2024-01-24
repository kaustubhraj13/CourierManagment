//package dao;
//
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import entity.Courier;
//import exception.TrackingNumberNotFoundException;
//
//public class CourierUserServiceImpl implements ICourierUserService {
//
//    private CourierUserServiceDb courierUserServiceDb;
//	private List<Courier> courierOrders;
//	
////	
////
////    public CourierUserServiceImpl() {
////        this.courierUserServiceDb = new CourierUserServiceDb();
////    }
//
//    @Override
//    public Courier getCourierByTrackingNumber(String trackingNumber) throws TrackingNumberNotFoundException {
//        return courierUserServiceDb.getCourierByTrackingNumber(trackingNumber);
//    }
//
////	@Override
////	public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
////		// TODO Auto-generated method stub
////		return null;
////	}
////    public CourierUserServiceImpl(List<Courier> courierOrders) {
////        this.courierOrders = courierOrders;
////    }
//    public CourierUserServiceImpl() {
//        // Initialize courierOrders in the constructor
//        this.courierOrders = new ArrayList<>(); // Or initialize it in a way suitable for your application
//    }
//    @Override
//    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
//        // Search for the order with the given trackingNumber
//        for (Courier courier : courierOrders) {
//            if (courier.getTrackingNumber().equals(trackingNumber)) {
//                return courier.getStatus();
//            }
//        }
//
//        // If the trackingNumber is not found, throw an exception
//        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
//    }
////	@Override
////	public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
////		// TODO Auto-generated method stub
////		return false;
////	}
//    private List<Courier> courierOrders1; // Assuming you have a list of courier orders
//
//    // Constructor to initialize the list, you may have a different mechanism to initialize this
//    public void CourierUserServiceImpl1(List<Courier> courierOrders) {
//        this.courierOrders = courierOrders;
//    }
//
//    @Override
//    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
//        Iterator<Courier> iterator = courierOrders.iterator();
//
//        while (iterator.hasNext()) {
//            Courier courier = iterator.next();
//            if (courier.getTrackingNumber().equals(trackingNumber)) {
//                // Assuming you have a status field in Courier class
//                if (courier.getStatus().equals("Delivered")) {
//                    // You may want to handle cancellation differently for delivered orders
//                    throw new IllegalStateException("Cannot cancel a delivered order.");
//                }
//
//                // Remove the order from the list since it's canceled
//                iterator.remove();
//                return true; // Order canceled successfully
//            }
//        }
//
//        // If the trackingNumber is not found, throw an exception
//        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
//    }
//    // Additional methods for user-related operations can be added here
//
////	public String placeOrder(String senderName, String senderAddress, String receiverName, String receiverAddress,
////			double weight) {
////		// TODO Auto-generated method stub
////		return null;
////	}
//
//    private static int trackingNumberCounter = 1; // Static counter for generating unique tracking numbers
//
//
//    public String placeOrder(String senderName, String senderAddress, String receiverName, String receiverAddress, double weight) {
//        // Generate a unique tracking number
//        String trackingNumber = generateTrackingNumber();
//
//        // Create a new Courier object with the provided information
//        Courier newOrder = new Courier(trackingNumber, senderName, senderAddress, receiverName, receiverAddress, weight);
//
//        // Add the new order to the list of courier orders
//        courierOrders.add(newOrder);
//
//        // Increment the tracking number counter for the next order
//        trackingNumberCounter++;
//
//        return trackingNumber;
//    }
//
//    // Helper method to generate a unique tracking number
//    private String generateTrackingNumber() {
//        return "TN" + trackingNumberCounter;
//    }
////	public List<Courier> getAssignedOrders(int courierStaffId) {
////		// TODO Auto-generated method stub
////		return null;
////	}
//	public List<Courier> getAssignedOrders(int courierStaffId) {
//        List<Courier> assignedOrders = new ArrayList<>();
//
//        for (Courier courier : courierOrders) {
//            // Assuming each Courier object has a property representing the courier staff ID
//            if (courier.getCourierID() == courierStaffId) {
//                assignedOrders.add(courier);
//            }
//        }
//
//        return assignedOrders;
//    }
//	
//}
package dao;

import entity.Courier;
import exception.TrackingNumberNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourierUserServiceImpl implements ICourierUserService {
	

    private List<Courier> courierOrders;
    private static int trackingNumberCounter = 1;
    public CourierUserServiceImpl() {
        // Initialize courierOrders in the constructor
        this.courierOrders = new ArrayList<>();
    }

    @Override
    public Courier getCourierByTrackingNumber(String trackingNumber) throws TrackingNumberNotFoundException {
        for (Courier courier : courierOrders) {
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                return courier;
            }
        }
        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        for (Courier courier : courierOrders) {
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                return courier.getStatus();
            }
        }
        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        Iterator<Courier> iterator = courierOrders.iterator();

        while (iterator.hasNext()) {
            Courier courier = iterator.next();
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                if ("Delivered".equals(courier.getStatus())) {
                    throw new IllegalStateException("Cannot cancel a delivered order.");
                }

                iterator.remove();
                return true;
            }
        }

        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }

    public String placeOrder(String senderName, String senderAddress, String receiverName, String receiverAddress, double weight) {
        String trackingNumber = generateTrackingNumber();
        Courier newOrder = new Courier(trackingNumber, senderName, senderAddress, receiverName, receiverAddress, weight);
        newOrder.setStatus("Order Placed with :" + trackingNumber);
        courierOrders.add(newOrder);
        return trackingNumber;
    }

    private String generateTrackingNumber() {

		return "TN" + trackingNumberCounter++;
    }

    public List<Courier> getAssignedOrders(int courierStaffId) {
        List<Courier> assignedOrders = new ArrayList<>();
        for (Courier courier : courierOrders) {
            if (courier.getCourierID() == courierStaffId) {
                assignedOrders.add(courier);
            }
        }
        return assignedOrders;
    }
}