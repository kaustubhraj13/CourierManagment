package exception;
public class InvalidEmployeeIdException extends Exception {

    public InvalidEmployeeIdException() {
        super("Invalid employee ID. Employee ID does not exist in the system.");
    }

    public InvalidEmployeeIdException(String message) {
        super(message);
    }
}
