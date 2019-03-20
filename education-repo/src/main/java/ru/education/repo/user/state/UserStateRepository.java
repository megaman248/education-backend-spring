package ru.education.repo.user.state;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.education.repo.RootRepository;

public interface UserStateRepository extends RootRepository<UserStateEntity, Long> {

    @Query("SELECT e FROM UserStateEntity e WHERE e.user.id = :userId ORDER BY e.id DESC")
    Page<UserStateEntity> findByUser(@Param("userId") long userId, Pageable pageable);
}
