package org.raflab.authservice.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
