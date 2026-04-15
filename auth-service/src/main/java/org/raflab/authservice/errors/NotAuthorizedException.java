package org.raflab.authservice.errors;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("Not Authorized");
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
