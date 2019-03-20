package ru.education.repo.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ru.education.repo.AbstractDataTest;
import ru.education.repo.person.PersonEntity;
import ru.education.repo.person.PersonRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.education.repo.person.PersonBuilder.person;
import static ru.education.repo.user.UserBuilder.user;

@Component
public class UsersTest extends AbstractDataTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userCreatedWithMinimalParams() {

        final long countUsersBeforeCreate = userRepository.count();

        PersonEntity firstNewPerson = person()
                .lastName("Иванов")
                .firstName("Иван")
                .buildIn(personRepository);
        PersonEntity secondNewPerson = person()
                .lastName("Иванов")
                .firstName("Иван")
                .buildIn(personRepository);

        UserEntity newAdministrator = user()
                .login("login1")
                .password("{noop}changeit")
                .person(firstNewPerson)
                .enabled(true)
                .buildIn(userRepository);
        UserEntity newManager = user()
                .login("login2")
                .password("{noop}changeit")
                .person(secondNewPerson)
                .enabled(true)
                .buildIn(userRepository);

        assertThat(userRepository.count()).isEqualTo(countUsersBeforeCreate + 2);

        UserEntity savedAdminisrtator = userRepository.getOne(newAdministrator.getId());
        assertThat(newAdministrator).isEqualTo(savedAdminisrtator);

        UserEntity savedManager = userRepository.getOne(newManager.getId());
        assertThat(newManager).isEqualTo(savedManager);
    }

    @Test
    public void testUniqueUserLogin() {
        final String notUniqueLogin = "superuser";

        PersonEntity firstNewPerson = person()
                .lastName("Иванов")
                .firstName("Иван")
                .buildIn(personRepository);
        PersonEntity secondNewPerson = person()
                .lastName("Иванов")
                .firstName("Иван")
                .buildIn(personRepository);

        user()
                .login(notUniqueLogin)
                .password("{noop}changeit")
                .person(firstNewPerson)
                .enabled(true)
                .buildIn(userRepository);

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() ->
                        user()
                                .login(notUniqueLogin)
                                .person(secondNewPerson)
                                .enabled(true)
                                .buildIn(userRepository)
                );
    }
}
