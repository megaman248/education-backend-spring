package ru.education.rest.api.person;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonRootName("person")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class PersonFull extends RepositoryDto implements Dto, Serializable {

    private static final long serialVersionUID = 1L;
    private String lastName;
    private String firstName;
    private String middleName;

    public PersonFull() {
        super();
    }

    public PersonFull(Long id) {
        this.id = id;
    }

    public PersonFull(PersonFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setLastName(source.getLastName());
        setFirstName(source.getFirstName());
        setMiddleName(source.getMiddleName());
    }
}
