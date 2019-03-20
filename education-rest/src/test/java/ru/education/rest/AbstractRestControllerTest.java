package ru.education.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

public abstract class AbstractRestControllerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    protected MockHttpSession session;
    @Autowired
    protected Environment environment;
    @Resource
    protected FilterChainProxy springSecurityFilterChain;
    @Autowired
    protected Slf4jMdcFilter slf4jMdcFilter;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    protected MockMvc mockMvc;

    protected RequestPostProcessor httpBasicAuthorization(final String username, final String password) {
        return request -> {
            final String value = username + ":" + password;
            request.addHeader("Authorization", "Basic " + Base64Utils.encodeToString(value.getBytes()));
            return request;
        };
    }

    protected RequestPostProcessor headerOriginLocalhost() {
        return request -> {
            request.addHeader("Origin", "http://localhost");
            return request;
        };
    }

    protected String getRootLogin() {
        return environment.getProperty("spring.security.user.name");
    }

    protected String getRootPassword() {
        return environment.getProperty("spring.security.user.password");
    }

    protected String encodedBcryptPassword(final String password) {
        return passwordEncoder.encode(password);
    }

    protected long resourceIdFromLocationHeader(final MvcResult mvcResult) {
        String headerValue = mvcResult.getResponse().getHeader("Location");
        return Long.parseLong(headerValue.substring(headerValue.lastIndexOf("/") + 1));
    }
}
