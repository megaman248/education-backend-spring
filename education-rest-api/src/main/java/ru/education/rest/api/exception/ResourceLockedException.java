package ru.education.rest.api.exception;

public class ResourceLockedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceLockedException() {
    }

    public ResourceLockedException(final String message) {
        super(message);
    }

    public ResourceLockedException(final Throwable cause) {
        super(cause);
    }

    public ResourceLockedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
