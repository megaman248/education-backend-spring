package ru.education.repo.locale;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.country.CountryEntity;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "locale")
public class LocaleEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "language", length = 2)
    private String language;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private CountryEntity country;

    @Column(name = "enabled")
    private Boolean enabled;

    @Version
    @Column(name = "version")
    private Integer version;

    public LocaleEntity() {
        super();
    }

    public LocaleEntity(String language, CountryEntity country) {
        this.language = language;
        this.country = country;
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
        final LocaleEntity other = (LocaleEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.language, other.language)
                && Objects.equals(this.version, other.version);
    }
}
