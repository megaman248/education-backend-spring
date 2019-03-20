package ru.education.rest.api.exception;

public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceConflictException(final String message) {
        super(message);
    }

    public ResourceConflictException(final Throwable cause) {
        super(cause);
    }

    public ResourceConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
