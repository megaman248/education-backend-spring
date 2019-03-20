package ru.education.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.education"})
@PropertySource("classpath:/changeable-rest.properties")
@PropertySource(value = "file:${catalina.base}/conf/education-rest.properties", ignoreResourceNotFound = true)
public class RestConfig implements WebMvcConfigurer {
}
