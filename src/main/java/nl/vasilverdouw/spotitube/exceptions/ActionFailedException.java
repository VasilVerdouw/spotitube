package nl.vasilverdouw.spotitube.exceptions;

public class ActionFailedException extends RuntimeException {
    public ActionFailedException(String message) {
        super(message);
    }
}
