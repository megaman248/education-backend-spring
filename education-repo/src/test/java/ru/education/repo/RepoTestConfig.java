package ru.education.repo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.education.repo")
@TestPropertySource("classpath:/changeable-repo.properties")
public class RepoTestConfig {
}
