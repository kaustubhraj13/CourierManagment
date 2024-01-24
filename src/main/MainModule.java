package main;
import java.util.List;
//
//import java.util.Scanner;
//
//import dao.CourierAdminServiceImpl;
//
//public class MainModule {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        CourierAdminServiceImpl courierAdminService = new CourierAdminServiceImpl();
//
//        int choice;
//        do {
//            System.out.println("Courier Management System Menu");
//            System.out.println("1. Place an order");
//            System.out.println("2. Get order status");
//            System.out.println("3. Cancel an order");
//            System.out.println("4. Add courier staff");
//            System.out.println("0. Exit");
//            System.out.print("Enter your choice: ");
//            
//            choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//
//            switch (choice) {
//                case 1:
//                    // Place an order logic
//                    System.out.println("Placing an order...");
//                    break;
//                case 2:
//                    // Get order status logic
//                    System.out.println("Getting order status...");
//                    break;
//                case 3:
//                    // Cancel an order logic
//                    System.out.println("Canceling an order...");
//                    break;
//                case 4:
//                    // Add courier staff logic
//                    System.out.println("Adding courier staff...");
//                    break;
//                case 0:
//                    System.out.println("Exiting the Courier Management System. Goodbye!");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 0);
//
//        // Close resources (e.g., scanner)
//        scanner.close();
//    }
//}
import java.util.Scanner;

import dao.CourierUserServiceImpl;
import entity.Courier;
import exception.TrackingNumberNotFoundException;

public class MainModule {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourierUserServiceImpl courierUserService = new CourierUserServiceImpl();

        int choice;

        do {
            System.out.println("\nCourier Management System Menu");
            System.out.println("1. Get Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. Place Courier Order");
            System.out.println("4. Get Assigned Orders");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Tracking Number: ");
                    String trackingNumber = scanner.nextLine();
                    try {
                        String orderStatus = courierUserService.getOrderStatus(trackingNumber);
                        System.out.println("Order Status: " + orderStatus);
                    } catch (TrackingNumberNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Enter Tracking Number to Cancel: ");
                    trackingNumber = scanner.nextLine();
                    try {
                        boolean isCanceled = courierUserService.cancelOrder(trackingNumber);
                        if (isCanceled) {
                            System.out.println("Order Canceled Successfully.");
                        } else {
                            System.out.println("Order could not be canceled.");
                        }
                    } catch (TrackingNumberNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                 // Inside the switch statement in the MainModule class
                case 3:
                    // Collect information for placing a courier order
                    System.out.println("Enter Sender Name: ");
                    String senderName = scanner.nextLine();

                    System.out.println("Enter Sender Address: ");
                    String senderAddress = scanner.nextLine();

                    System.out.println("Enter Receiver Name: ");
                    String receiverName = scanner.nextLine();

                    System.out.println("Enter Receiver Address: ");
                    String receiverAddress = scanner.nextLine();

                    System.out.println("Enter Weight of the Parcel: ");
                    double weight = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    // Assuming courierUserService has a method for placing an order
                    String trackingNumber1 = courierUserService.placeOrder(senderName, senderAddress, receiverName, receiverAddress, weight);
                    System.out.println("Order Placed Successfully. Tracking Number: " + trackingNumber1);
                    break;


                 // Inside the switch statement in the MainModule class
                case 4:
                    // Collect information for getting assigned orders
                    System.out.println("Enter Courier ID: ");
                    int courierStaffId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Assuming courierUserService has a method for getting assigned orders
                    List<Courier> assignedOrders = courierUserService.getAssignedOrders(courierStaffId);

                    if (assignedOrders.isEmpty()) {
                        System.out.println("No orders assigned to courier staff with ID " + courierStaffId);
                    } else {
                        System.out.println("Orders assigned to courier staff with ID " + courierStaffId + ":");
                        for (Courier order : assignedOrders) {
                            System.out.println("Tracking Number: " + order.getTrackingNumber() + ", Status: " + order.getStatus());
                        }
                    }
                    break;


                case 5:
                    System.out.println("Exiting Courier Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }
}


