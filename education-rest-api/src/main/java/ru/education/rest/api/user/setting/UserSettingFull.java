package ru.education.rest.api.user.setting;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;
import ru.education.rest.api.locale.LocaleFull;
import ru.education.rest.api.timezone.TimeZoneFull;
import ru.education.rest.api.user.UserFull;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonRootName("user_setting")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class UserSettingFull extends RepositoryDto implements Dto, Serializable {

    private UserFull user;
    private LocaleFull locale;
    private TimeZoneFull timeZone;
    private Boolean enabled;

    public UserSettingFull() {
        super();
    }

    public UserSettingFull(UserSettingFull source) {
        super(source);
        if (source == null) {
            return;
        }
        if (source.getUser() != null) {
            setUser(new UserFull(source.getUser()));
        }
        if (source.getLocale() != null) {
            setLocale(new LocaleFull(source.getLocale()));
        }
        if (source.getTimeZone() != null) {
            setTimeZone(new TimeZoneFull(source.getTimeZone()));
        }
        setEnabled(source.getEnabled());
    }
}
