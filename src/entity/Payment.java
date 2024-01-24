package entity;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int courierID;
    private int locationId;
    private double amount;
    private Date paymentDate;

    public Payment(int paymentID, int courierID, int locationId, double amount, Date paymentDate) {
        this.paymentID = paymentID;
        this.courierID = courierID;
        this.locationId = locationId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", courierID=" + courierID +
                ", locationId=" + locationId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}

