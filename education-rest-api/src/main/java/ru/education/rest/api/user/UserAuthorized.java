package ru.education.rest.api.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.education.rest.api.Dto;
import ru.education.rest.api.group.GroupFull;
import ru.education.rest.api.person.PersonFull;
import ru.education.rest.api.user.setting.UserSettingFull;
import ru.education.rest.api.user.state.UserStateFull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonRootName("user")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class UserAuthorized implements Dto, Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @EqualsAndHashCode.Include
    private String login;
    private String password;
    private PersonFull person;
    private Date validTill;
    private UserStateFull lastState;
    private Boolean enabled;
    private UserSettingFull setting;
    private List<GroupFull> groups;
}
