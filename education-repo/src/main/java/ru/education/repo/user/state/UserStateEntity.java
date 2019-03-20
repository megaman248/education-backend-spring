package ru.education.repo.user.state;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.user.UserEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@Entity
@Table(name = "users_state")
public class UserStateEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "code")
    private Integer code;

    @Temporal(TIMESTAMP)
    @Column(name = "started_at")
    private Date startedAt;

    @Temporal(TIMESTAMP)
    @Column(name = "ended_at")
    private Date endedAt;

    @Column(name = "note")
    private String note;

    @Version
    @Column(name = "version")
    private Integer version;

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
        final UserStateEntity other = (UserStateEntity) obj;
        return Objects.equals(this.code, other.code)
                && Objects.equals(this.startedAt, other.startedAt)
                && Objects.equals(this.endedAt, other.endedAt);
    }
}
