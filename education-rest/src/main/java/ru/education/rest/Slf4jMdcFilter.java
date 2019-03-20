package ru.education.rest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
public class Slf4jMdcFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        MDC.put("ip", remoteAddrFrom((HttpServletRequest) request));
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("ip");
        }
    }

    private String remoteAddrFrom(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        final String unknown = "unknown";
        if (isNullOrEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isNullOrEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isNullOrEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isNullOrEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        return getRemoteAddr(request, ip);
    }

    private String getRemoteAddr(final HttpServletRequest request, final String ip) {
        if (isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            return request.getRemoteAddr();
        }
        return ip;
    }
}
