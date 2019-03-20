package ru.education.repo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;

@Configuration
@EntityScan("ru.education.repo")
@EnableJpaRepositories("ru.education.repo")
@PropertySource("classpath:/changeable-repo.properties")
@PropertySource(value = "file:${catalina.base}/conf/education-repo.properties", ignoreResourceNotFound = true)
public class RepoConfig {

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
