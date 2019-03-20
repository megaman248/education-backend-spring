package ru.education.repo.user;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.group.GroupEntity;
import ru.education.repo.person.PersonEntity;
import ru.education.repo.user.setting.UserSettingEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "login", length = 20)
    private String login;

    @Column(name = "password", length = 32)
    private String password;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Temporal(TIMESTAMP)
    @Column(name = "valid_till")
    private Date validTill;

    @Column(name = "enabled")
    private Boolean enabled;

    @Version
    @Column(name = "version")
    private Integer version;

    @OneToOne(mappedBy = "user")
    private UserSettingEntity setting;

    @ManyToMany(cascade = MERGE)
    @JoinTable(name = "users_groups", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<GroupEntity> groups;

    public UserEntity() {
        super();
    }

    public UserEntity(String login) {
        this.login = login;
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
        final UserEntity other = (UserEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.login, other.login)
                && Objects.equals(this.version, other.version);
    }
}
