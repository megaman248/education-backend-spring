package ru.education.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("ru.education.service")
@EnableTransactionManagement
@PropertySource("classpath:/changeable-service.properties")
@PropertySource(value = "file:${catalina.base}/conf/education-service.properties", ignoreResourceNotFound = true)
public class ServiceConfig {
}
