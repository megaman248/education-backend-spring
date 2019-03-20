package ru.education.rest.api.exception;

public class NoContentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoContentException() {
    }

    public NoContentException(final String message) {
        super(message);
    }

    public NoContentException(final Throwable cause) {
        super(cause);
    }

    public NoContentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
