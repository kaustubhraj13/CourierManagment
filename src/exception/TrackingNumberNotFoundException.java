package exception;

public class TrackingNumberNotFoundException extends Exception {

    public TrackingNumberNotFoundException() {
        super("Tracking number not found.");
    }

    public TrackingNumberNotFoundException(String trackingNumber) {
        super("Tracking number not found: " + trackingNumber);
    }
}
