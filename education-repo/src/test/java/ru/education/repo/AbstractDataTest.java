package ru.education.repo;

import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@Component
@ContextConfiguration(classes = {RepoTestConfig.class})
public abstract class AbstractDataTest extends AbstractTransactionalJUnit4SpringContextTests {
}
