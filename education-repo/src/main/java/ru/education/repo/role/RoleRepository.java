package ru.education.repo.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.education.repo.RootRepository;

import java.util.List;

public interface RoleRepository extends RootRepository<RoleEntity, Long> {

    @Query("SELECT e.roles FROM GroupEntity e WHERE e.id = :groupId")
    List<RoleEntity> getByGroupId(@Param("groupId") long id);
}
