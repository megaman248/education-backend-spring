package ru.education.rest.api.exception;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
    }

    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final Throwable cause) {
        super(cause);
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
