package ru.education.repo.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.education.repo.RootRepository;

@Repository
public interface UserRepository extends RootRepository<UserEntity, Long> {

    @Query("SELECT e FROM UserEntity e WHERE e.login = :login")
    UserEntity findByLogin(@Param("login") String login);
}
