package ru.education.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public abstract class AbstractServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    protected Pageable defaultPageable() {
        return PageRequest.of(0, 20);
    }
}
