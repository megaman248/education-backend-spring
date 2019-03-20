package ru.education.rest.exception;

import ch.qos.logback.classic.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.education.rest.api.exception.ForbiddenException;
import ru.education.rest.api.exception.ResourceConflictException;
import ru.education.rest.api.exception.ResourceLockedException;
import ru.education.rest.api.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ClientStatusHandler extends AbstractStatusHandler {

    private static final Logger LOG = (Logger) getLogger(ClientStatusHandler.class);

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleResourceNotFound(NoHandlerFoundException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbidden(ForbiddenException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public String handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String handleValidation(ValidationException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ResourceConflictException.class)
    public String handleResourceConflict(ResourceConflictException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(LOCKED)
    @ExceptionHandler(ResourceLockedException.class)
    public String handleResourceLocked(ResourceLockedException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }
}
