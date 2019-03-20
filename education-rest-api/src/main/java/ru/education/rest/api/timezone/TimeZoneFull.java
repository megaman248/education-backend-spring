package ru.education.rest.api.timezone;

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
@JsonRootName("country")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class TimeZoneFull extends RepositoryDto implements Dto, Serializable {

    private String utc;
    private Boolean enabled;

    public TimeZoneFull() {
        super();
    }

    public TimeZoneFull(TimeZoneFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setUtc(source.getUtc());
        setEnabled(source.getEnabled());
    }
}
