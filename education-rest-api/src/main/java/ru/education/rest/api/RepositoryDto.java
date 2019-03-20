package ru.education.rest.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@JsonRootName("repository_dto")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public abstract class RepositoryDto implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Long id;
    protected Integer version;

    public RepositoryDto() {
    }

    public RepositoryDto(RepositoryDto source) {
        if (source == null) {
            return;
        }
        setId(source.getId());
        setVersion(source.getVersion());
    }
}
