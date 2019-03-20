package ru.education.rest.api.country;

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
public class CountryFull extends RepositoryDto implements Dto, Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String iso2;
    private String iso3;
    private Boolean enabled;

    public CountryFull() {
        super();
    }

    public CountryFull(CountryFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setCode(source.getCode());
        setIso2(source.getIso2());
        setIso3(source.getIso3());
        setEnabled(source.getEnabled());
    }
}
