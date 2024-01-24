package dao;

import entity.Courier;
import exception.TrackingNumberNotFoundException;

public interface ICourierUserService {

    Courier getCourierByTrackingNumber(String trackingNumber) throws TrackingNumberNotFoundException;

    String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException;

    boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException;

    // Additional methods for user-related operations can be added here
}