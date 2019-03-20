package ru.education.rest.api.role;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonRootName("role")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class RoleFull extends RepositoryDto implements Dto, Serializable {

    private String codeName;

    public RoleFull() {
        super();
    }

    public RoleFull(RoleFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setCodeName(source.getCodeName());
    }
}
