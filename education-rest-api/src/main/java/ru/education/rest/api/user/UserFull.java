package ru.education.rest.api.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;
import ru.education.rest.api.group.GroupFull;
import ru.education.rest.api.person.PersonFull;
import ru.education.rest.api.user.setting.UserSettingFull;
import ru.education.rest.api.user.state.UserStateFull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@JsonRootName("user")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class UserFull extends RepositoryDto implements Dto, Serializable {

    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Include
    private String login;
    private String password;
    private PersonFull person;
    private Date validTill;
    private UserStateFull lastState;
    private Boolean enabled;
    @ToString.Exclude
    private UserSettingFull setting;
    @ToString.Exclude
    private List<GroupFull> groups;

    public UserFull() {
        super();
    }

    public UserFull(Long id) {
        this.id = id;
    }

    public UserFull(UserFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setLogin(source.getLogin());
        setPassword(source.getPassword());
        setPerson(new PersonFull(source.getPerson()));
        if (source.getValidTill() != null) {
            setValidTill(new Date(source.getValidTill().getTime()));
        }
        if (source.getLastState() != null) {
            setLastState(new UserStateFull(source.getLastState()));
        }
        if (source.getSetting() != null) {
            setSetting(new UserSettingFull(source.getSetting()));
        }
        setEnabled(source.getEnabled());
        if (source.getGroups() != null) {
            for (GroupFull item : source.getGroups()) {
                addGroup(new GroupFull(item));
            }
        }
    }

    public void addGroup(GroupFull group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }
}
