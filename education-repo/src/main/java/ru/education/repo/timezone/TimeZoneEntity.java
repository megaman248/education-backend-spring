package ru.education.repo.timezone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "timezone")
public class TimeZoneEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "utc")
    private String utc;

    @Column(name = "enabled")
    private Boolean enabled;

    @Version
    @Column(name = "version")
    private Integer version;

    public TimeZoneEntity() {
        super();
    }

    public TimeZoneEntity(String utc) {
        this.utc = utc;
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
        final TimeZoneEntity other = (TimeZoneEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.utc, other.utc)
                && Objects.equals(this.enabled, other.enabled)
                && Objects.equals(this.version, other.version);
    }
}
