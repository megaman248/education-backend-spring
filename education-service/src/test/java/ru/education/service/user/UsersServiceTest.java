package ru.education.service.user;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import ru.education.service.AbstractServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Feature("Бизнесс правила")
@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest extends AbstractServiceTest {

    @Mock
    private UserService userService;

    @Test
    @Story("Простая проверка")
    public void simpleTest() {
        Object actualData = null;
        assertThat(actualData).isNull();

        actualData = "String value";
        assertThat(actualData).isNotNull();
    }

    @Test
    @Story("Получение списка пользователей")
    public void getUsers() {
        Pageable pageable = defaultPageable();

        userService.findUsers(pageable);

        verify(userService).findUsers(eq(pageable));

        verifyNoMoreInteractions(userService);
        verifyZeroInteractions(userService);
    }
}
