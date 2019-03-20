package ru.education.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import ru.education.rest.exception.ErrorJsonMessage;
import ru.education.service.user.CustomUserDetailsService;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("root").password("{bcrypt}$2a$10$ozs8PePF36zdYf9giH9dH.i884KrgDRBiCQKWXcLovY0kSC4RY1f2").roles("ROOT");
//        auth.inMemoryAuthentication().withUser("user").password("{noop}changeit").roles("USER_READ_LIST", "USER_READ");
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class)
                .authorizeRequests()
                .antMatchers("/guest/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and().httpBasic().realmName("Education REST Services")
                .authenticationEntryPoint(authenticationEntryPoint());

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/**/favicon.ico");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, exception) -> {
            response.setStatus(SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON_UTF8_VALUE);
            String result;
            if (exception instanceof InsufficientAuthenticationException) {
                result = new ErrorJsonMessage(request.getMethod(), request.getRequestURL().toString(), "Указан неправильный логин или пароль").get();
            } else {
                result = new ErrorJsonMessage(request.getMethod(), request.getRequestURL().toString(), exception.getMessage()).get();
            }
            response.getOutputStream().write(result.getBytes(UTF_8));
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, exception) -> {
            response.setStatus(SC_FORBIDDEN);
            response.setContentType(APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream()
                    .write(new ErrorJsonMessage(request.getMethod(), request.getRequestURL().toString(), exception.getMessage()).get().getBytes(UTF_8));
        };
    }
}
