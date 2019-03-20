package ru.education.rest.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.singleton;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.servlet.HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE;

abstract class AbstractStatusHandler {

    private void addCORSHeaders(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        if (origin != null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Max-Age", "1");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "deny");
        response.setHeader("X-XSS-Protection", "1");
        response.setHeader("Author", "Java Programmer <java@domain.ru>");
    }

    String message(Exception e, HttpServletRequest request, HttpServletResponse response) {
        addCORSHeaders(request, response);
        request.setAttribute(PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, singleton(APPLICATION_JSON_UTF8));
        return new ErrorJsonMessage(request.getMethod(), request.getRequestURL().toString(), e.getLocalizedMessage()).get();
    }
}
