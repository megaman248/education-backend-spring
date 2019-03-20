package ru.education.rest;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractRestController {
    @Autowired
    protected HttpServletRequest httpServletRequest;
    @Autowired
    protected HttpServletResponse httpServletResponse;

    protected String buildHeaderLocation(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpServletRequest.getProtocol()).append("://").append(httpServletRequest.getRemoteHost());

        int port = httpServletRequest.getRemotePort();
        if (port != 80 && port != 443) {
            sb.append(":").append(port);
        }
        sb.append(httpServletRequest.getContextPath()).append(path);
        return sb.toString();
    }
}
