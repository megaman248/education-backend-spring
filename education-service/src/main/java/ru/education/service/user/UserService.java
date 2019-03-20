package ru.education.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.education.rest.api.user.UserFull;

import java.util.Optional;

public interface UserService {

    long create(UserFull user);

    Optional<UserFull> read(long id);

    long update(UserFull user);

    void delete(long id);

    Page<UserFull> findUsers(Pageable pageable);
}
