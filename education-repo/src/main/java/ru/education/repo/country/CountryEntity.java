package ru.education.repo.country;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "iso2", length = 2)
    private String iso2;

    @Column(name = "iso3", length = 3)
    private String iso3;

    @Column(name = "enabled")
    private Boolean enabled;

    @Version
    @Column(name = "version")
    private Integer version;

    public CountryEntity() {
        super();
    }

    public CountryEntity(String iso2) {
        this.iso2 = iso2;
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
        final CountryEntity other = (CountryEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.iso2, other.iso2)
                && Objects.equals(this.version, other.version);
    }
}
