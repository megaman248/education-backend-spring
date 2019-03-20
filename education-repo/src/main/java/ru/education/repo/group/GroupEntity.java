package ru.education.repo.group;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.role.RoleEntity;
import ru.education.repo.user.UserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "code_name", length = 50)
    private String codeName;

    @Version
    @Column(name = "version")
    private Integer version;

    @OneToMany
    @JoinTable(name = "users_groups", joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "groups_roles", joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<RoleEntity> roles;

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroupEntity other = (GroupEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.codeName, other.codeName)
                && Objects.equals(this.version, other.version);
    }
}
