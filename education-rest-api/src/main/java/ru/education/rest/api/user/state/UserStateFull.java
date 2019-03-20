package ru.education.rest.api.user.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.education.rest.api.Dto;
import ru.education.rest.api.RepositoryDto;
import ru.education.rest.api.user.UserFull;

import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonRootName("user_state")
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE, isGetterVisibility = NONE)
public class UserStateFull extends RepositoryDto implements Dto, Serializable {

    private static final long serialVersionUID = 1L;
    private UserFull user;
    private UserStateType type;
    private Date stateStart;
    private Date stateEnd;
    private String note;

    public UserStateFull() {
        super();
    }

    public UserStateFull(UserStateType type) {
        this.type = type;
    }

    public UserStateFull(UserStateFull source) {
        super(source);
        if (source == null) {
            return;
        }
        setType(source.getType());
        if (source.getStateStart() != null) {
            setStateStart(new Date(source.getStateStart().getTime()));
        }
        if (source.getStateEnd() != null) {
            setStateEnd(new Date(source.getStateEnd().getTime()));
        }
        setNote(source.getNote());
    }
}
