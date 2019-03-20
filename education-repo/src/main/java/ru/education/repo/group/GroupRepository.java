package ru.education.repo.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.education.repo.RootRepository;

public interface GroupRepository extends RootRepository<GroupEntity, Long> {

    @Query("SELECT e.groups FROM UserEntity e WHERE e.id = :userId")
    Page<GroupEntity> findAllByUser(@Param("userId") int id, Pageable pageable);

    @Query("SELECT e FROM GroupEntity e WHERE e.codeName = :codeName")
    GroupEntity findGroupByCodeName(@Param("codeName") String codeName);
}
