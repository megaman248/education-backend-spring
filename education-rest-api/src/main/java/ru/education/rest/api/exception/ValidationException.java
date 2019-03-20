package ru.education.rest.api.exception;

public class ValidationException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final Throwable cause) {
        super(cause);
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
