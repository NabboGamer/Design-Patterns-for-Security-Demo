package it.unibas.exception;

public class InsufficientPermissionException extends Exception {

    public InsufficientPermissionException(Throwable cause) {
        super(cause);
    }

    public InsufficientPermissionException(String message) {
        super(message);
    }

    public InsufficientPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

}