package ru.education.rest.api.locale;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;
import ru.education.rest.api.country.CountryFull;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonRootName("locale")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class LocaleFull extends RepositoryDto implements Dto, Serializable {

    private static final long serialVersionUID = 1L;
    private String language;
    @EqualsAndHashCode.Exclude
    private CountryFull country;
    private Boolean enabled;

    public LocaleFull() {
        super();
    }

    public LocaleFull(LocaleFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setLanguage(source.getLanguage());
        setCountry(new CountryFull(source.getCountry()));
        setEnabled(source.getEnabled());
    }
}
