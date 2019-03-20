package ru.education.repo.training;

import lombok.Getter;
import lombok.Setter;
import ru.education.repo.faculty.FacultyEntity;
import ru.education.repo.training.tag.TrainingTagEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@Entity
@Table(name = "training")
public class TrainingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Temporal(TIMESTAMP)
    @Column(name = "opened_at")
    private Date openedAt;

    @Temporal(TIMESTAMP)
    @Column(name = "closed_at")
    private Date closedAt;

    @ManyToOne
    @JoinColumn(name = "faculty_id", insertable = false, updatable = false)
    private FacultyEntity faculty;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Version
    @Column(name = "version")
    private Integer version;

    @ManyToMany
    @JoinTable(name = "trainings_tags", joinColumns = {@JoinColumn(name = "training_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<TrainingTagEntity> tags;

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
        final TrainingEntity other = (TrainingEntity) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.enabled, other.enabled)
                && Objects.equals(this.version, other.version);
    }
}
