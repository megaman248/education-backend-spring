package ru.education.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("ru.education.service")
@EnableTransactionManagement
@TestPropertySource("classpath:/changeable-service.properties")
public class ServiceTestConfig {
}
