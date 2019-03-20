package ru.education.rest.exception;

import ch.qos.logback.classic.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ServerStatusHandler extends AbstractStatusHandler {

    private static final Logger LOG = (Logger) getLogger(ClientStatusHandler.class);

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccess(DataAccessException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public String handleService(ServiceException e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return message(e, request, response);
    }
}
