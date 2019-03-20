package ru.education.repo.user.setting;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.locale.LocaleEntity;
import ru.education.repo.timezone.TimeZoneEntity;
import ru.education.repo.user.UserEntity;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "users_setting")
public class UserSettingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @OneToOne
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "locale_id")
    private LocaleEntity locale;

    @ManyToOne
    @JoinColumn(name = "timezone_id")
    private TimeZoneEntity timeZone;

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
        final UserSettingEntity other = (UserSettingEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.version, other.version);
    }
}
