package ru.education.repo.role;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.group.GroupEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity {

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
    @JoinTable(name = "groups_roles", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<GroupEntity> groups;

    public RoleEntity() {
        super();
    }

    public RoleEntity(String codeName) {
        this.codeName = codeName;
    }

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
        final RoleEntity other = (RoleEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.codeName, other.codeName)
                && Objects.equals(this.version, other.version);
    }
}
